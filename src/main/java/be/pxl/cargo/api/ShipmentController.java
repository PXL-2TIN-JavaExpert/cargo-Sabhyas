package be.pxl.cargo.api;

import be.pxl.cargo.service.ShipmentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shipments")
public class ShipmentController {

    private final ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @PostMapping("/{shipmentId}/cargo/{cargoCode}")
    public void addCargoToShipment(@PathVariable Long shipmentId, @PathVariable String cargoCode) {
        this.shipmentService.addCargoToShipment(shipmentId, cargoCode);
    }

    @PutMapping("/{shipmentId}/arrive")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void markShipmentAsArrived(@PathVariable Long shipmentId) {
        this.shipmentService.markShipmentAsArrived(shipmentId);
    }
}
