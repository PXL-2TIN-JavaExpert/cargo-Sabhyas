package be.pxl.cargo.service;

import be.pxl.cargo.api.response.ShipmentStatistics;
import be.pxl.cargo.domain.Cargo;
import be.pxl.cargo.domain.Shipment;
import be.pxl.cargo.exceptions.ResourceNotFoundException;
import be.pxl.cargo.repository.CargoRepository;
import be.pxl.cargo.repository.ShipmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final CargoRepository cargoRepository;

    public ShipmentService(ShipmentRepository shipmentRepository, CargoRepository cargoRepository) {
        this.shipmentRepository = shipmentRepository;
        this.cargoRepository = cargoRepository;
    }


    @Transactional
    public void addCargoToShipment(Long shipmentId, String cargoCode) {
        Shipment shipment = shipmentRepository.findById(shipmentId).orElseThrow(ResourceNotFoundException::new);
        Cargo cargo = cargoRepository.findCargoByCode(cargoCode).orElseThrow(ResourceNotFoundException::new);
        shipment.addCargo(cargo);

    }

    @Transactional
    public void markShipmentAsArrived(Long shipmentId) {
        Shipment shipment = shipmentRepository.findById(shipmentId).orElseThrow(ResourceNotFoundException::new);
        shipment.setArrived();

    }

    public ShipmentStatistics getShipmentStatistics() {
        List<Shipment> allShipments = shipmentRepository.findAll();
        ShipmentStatistics shipmentStatistics = new ShipmentStatistics();
        shipmentStatistics.setNumberOfShipments(allShipments.size());
        shipmentStatistics.setArrived(allShipments.stream().filter(Shipment::isArrived).count());
        shipmentStatistics.setAvgCapacityUtilization(allShipments.stream().mapToDouble(Shipment::getCapacityUtilization).average().orElse(0.0));
        shipmentStatistics.setDistinctEndLocations(allShipments.stream().map(Shipment::getEndLocation).distinct().toList());
        return shipmentStatistics;
    }
}
