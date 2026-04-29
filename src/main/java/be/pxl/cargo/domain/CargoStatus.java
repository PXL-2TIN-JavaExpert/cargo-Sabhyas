package be.pxl.cargo.domain;

public enum CargoStatus {
    CREATED,               // Initial state
    AT_TRANSIT_POINT,      // Cargo is at an intermediate location, not moving
    MOVING,                // Cargo is actively being transported
    DELIVERED              // Cargo has reached its destination
}
