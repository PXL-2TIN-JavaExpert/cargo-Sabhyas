package be.pxl.cargo.api;

import be.pxl.cargo.api.request.CreateCargoRequest;
import be.pxl.cargo.api.response.CargoStatistics;
import be.pxl.cargo.service.CargoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cargos")
public class CargoController {

    private final CargoService cargoService;

    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addCargo(@RequestBody @Valid CreateCargoRequest createCargoRequest) {
        cargoService.createCargo(createCargoRequest);
    }

    @GetMapping("/statistics")
    public CargoStatistics getCargoStatistics() {
        return cargoService.getCargoStatistics();
    }
}
