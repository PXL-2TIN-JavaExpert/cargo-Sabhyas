package be.pxl.cargo.domain;

import be.pxl.cargo.exceptions.ShipmentException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ShipmentTest {

    @Test
    void testAddCargoThrowsShipmentExceptionIfWrongLocation(){
        Shipment shipment = new Shipment(Location.SEA_PORT_Z, Location.WAREHOUSE_A, 1000.0);
        Cargo cargo = new Cargo("CARGO_0", 500, Location.AIRPORT_X, Location.CITY_B);
        assertThrows(be.pxl.cargo.exceptions.ShipmentException.class, () -> shipment.addCargo(cargo));
    }

    @Test
    void testAddCargoGetsStatusMoving(){
        Shipment shipment = new Shipment(Location.SEA_PORT_Z, Location.WAREHOUSE_A, 1000.0);
        Cargo cargo = new Cargo("CARGO_0", 500, Location.SEA_PORT_Z, Location.CITY_B);
        shipment.addCargo(cargo);
        assertEquals(CargoStatus.MOVING, cargo.getCargoStatus());

    }

    @Test
    void testAddCargoThrowsShipmentExceptionIfCapacityTooSmall(){
        Shipment shipment = new Shipment(Location.SEA_PORT_Z, Location.WAREHOUSE_A, 1000.0);
        Cargo cargo = new Cargo("CARGO_0", 2000.0, Location.SEA_PORT_Z, Location.CITY_B);
        assertThrows(ShipmentException.class, () -> shipment.addCargo(cargo));
    }
}
