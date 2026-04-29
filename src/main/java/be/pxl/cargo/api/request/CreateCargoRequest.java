package be.pxl.cargo.api.request;

import be.pxl.cargo.domain.Location;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCargoRequest(@NotBlank String code, @Min(100) double weight, @NotNull Location origin, @NotNull Location destination) {
}
