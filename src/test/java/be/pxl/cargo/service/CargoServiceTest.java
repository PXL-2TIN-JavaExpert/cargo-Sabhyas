package be.pxl.cargo.service;

import be.pxl.cargo.api.request.CreateCargoRequest;
import be.pxl.cargo.api.response.CargoStatistics;
import be.pxl.cargo.domain.Cargo;
import be.pxl.cargo.domain.CargoStatus;
import be.pxl.cargo.domain.Location;
import be.pxl.cargo.exceptions.NonUniqueCodeException;
import be.pxl.cargo.repository.CargoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CargoServiceTest {

    @Mock
    private CargoRepository cargoRepository;

    @InjectMocks
    private CargoService cargoService;

    @Test
    void createCargoSavesNewCargoWhenCodeIsUnique() {
        CreateCargoRequest request = new CreateCargoRequest("CARGO_01", 500, Location.SEA_PORT_Z, Location.CITY_B);
        when(cargoRepository.findCargoByCode("CARGO_01")).thenReturn(Optional.empty());

        cargoService.createCargo(request);

        ArgumentCaptor<Cargo> cargoCaptor = ArgumentCaptor.forClass(Cargo.class);
        verify(cargoRepository).save(cargoCaptor.capture());
        Cargo savedCargo = cargoCaptor.getValue();
        assertEquals("CARGO_01", savedCargo.getCode());
        assertEquals(500, savedCargo.getWeight());
        assertEquals(Location.SEA_PORT_Z, savedCargo.getOrigin());
        assertEquals(Location.CITY_B, savedCargo.getDestination());
    }

    @Test
    void createCargoThrowsWhenCodeAlreadyExists() {
        CreateCargoRequest request = new CreateCargoRequest("CARGO_01", 500, Location.SEA_PORT_Z, Location.CITY_B);
        when(cargoRepository.findCargoByCode("CARGO_01")).thenReturn(Optional.of(new Cargo("CARGO_01", 500, Location.SEA_PORT_Z, Location.CITY_B)));

        assertThrows(NonUniqueCodeException.class, () -> cargoService.createCargo(request));
    }

    @Test
    void getCargoStatisticsCalculatesAggregates() {
        Cargo movingCargo = new Cargo("CARGO_01", 500, Location.SEA_PORT_Z, Location.CITY_B);
        Cargo transitCargo = new Cargo("CARGO_02", 800, Location.SEA_PORT_Z, Location.CITY_B);
        transitCargo.arrive(Location.WAREHOUSE_A);
        Cargo deliveredCargo = new Cargo("CARGO_03", 1200, Location.SEA_PORT_Z, Location.CITY_B);
        deliveredCargo.arrive(Location.CITY_B);

        when(cargoRepository.findAll()).thenReturn(List.of(movingCargo, transitCargo, deliveredCargo));

        CargoStatistics statistics = cargoService.getCargoStatistics();

        assertEquals(Map.of(CargoStatus.CREATED, 1L, CargoStatus.AT_TRANSIT_POINT, 1L, CargoStatus.DELIVERED, 1L), statistics.getStatusCount());
        assertEquals("CARGO_03", statistics.getHeaviestCargo());
        assertEquals(833.3333333333334, statistics.getAverageCargoWeight(), 0.0001);
        assertEquals(1L, statistics.getCountCargosAtWarehouseA());
        assertEquals(1200.0, statistics.getTotalWeightDeliveredAtCityB(), 0.0001);
    }
}
