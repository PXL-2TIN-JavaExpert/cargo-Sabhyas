package be.pxl.cargo.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private double weight;
    @Enumerated(EnumType.STRING)
    private Location origin;
    @Enumerated(EnumType.STRING)
    private Location destination;
    @Enumerated(EnumType.STRING)
    private Location currentLocation;
    @Enumerated(EnumType.STRING)
    private CargoStatus cargoStatus;

    public Cargo() {
    }

    public Cargo(String code, double weight, Location origin, Location destination) {
        this.code = code;
        this.weight = weight;
        this.origin = origin;
        this.destination = destination;
        this.cargoStatus = CargoStatus.CREATED;
        this.currentLocation = origin;
    }

    public void arrive(Location location) {
        currentLocation = location;
        if (location.equals(destination)) {
            cargoStatus = CargoStatus.DELIVERED;
        } else {
            cargoStatus = CargoStatus.AT_TRANSIT_POINT;
        }
    }

    public String getCode() {
        return code;
    }

    public double getWeight() {
        return weight;
    }

    public Location getOrigin() {
        return origin;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public Location getDestination() {
        return destination;
    }

    public CargoStatus getCargoStatus() {
        return cargoStatus;
    }

    public void setCargoStatus(CargoStatus cargoStatus) {
        this.cargoStatus = cargoStatus;
    }
}
