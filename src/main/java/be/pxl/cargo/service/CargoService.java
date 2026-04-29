package be.pxl.cargo.service;

import be.pxl.cargo.api.request.CreateCargoRequest;
import be.pxl.cargo.api.response.CargoStatistics;
import be.pxl.cargo.domain.Cargo;
import be.pxl.cargo.domain.CargoStatus;
import be.pxl.cargo.domain.Location;
import be.pxl.cargo.exceptions.NonUniqueCodeException;
import be.pxl.cargo.repository.CargoRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CargoService {

    private final CargoRepository cargoRepository;

    public CargoService(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    public void createCargo(CreateCargoRequest createCargoRequest) {
        Optional<Cargo> cargoByCode = cargoRepository.findCargoByCode(createCargoRequest.code());
        if (cargoByCode.isPresent()) {
            throw new NonUniqueCodeException();
        }
        Cargo cargo = new Cargo(createCargoRequest.code(), createCargoRequest.weight(), createCargoRequest.origin(), createCargoRequest.destination());
        cargoRepository.save(cargo);
    }

    public CargoStatistics getCargoStatistics() {
        List<Cargo> allCargos = cargoRepository.findAll();
        CargoStatistics cargoStatistics = new CargoStatistics();
        Map<CargoStatus, Long> statusMap = allCargos.stream()
                .collect(Collectors.groupingBy(
                        Cargo::getCargoStatus,
                        Collectors.counting()
                ));
        cargoStatistics.setStatusCount(statusMap);
        Cargo cargo = allCargos.stream().max(Comparator.comparingDouble(Cargo::getWeight)).orElse(null);
        cargoStatistics.setHeaviestCargo(cargo != null? cargo.getCode() : null);
        cargoStatistics.setAverageCargoWeight(allCargos.stream().mapToDouble(Cargo::getWeight).average().orElse(0.0));
        cargoStatistics.setCountCargosAtWarehouseA(allCargos.stream().filter(c -> c.getCurrentLocation() == Location.WAREHOUSE_A).count());
        cargoStatistics.setTotalWeightDeliveredAtCityB(allCargos.stream().filter(c -> c.getCurrentLocation() == Location.CITY_B && c.getCargoStatus() == CargoStatus.DELIVERED).mapToDouble(Cargo::getWeight).sum());
        return cargoStatistics;
    }
}
