package be.pxl.cargo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ShipmentException extends RuntimeException {

    public ShipmentException(String message) {
        super(message);
    }
}
