package be.pxl.cargo.domain;

import be.pxl.cargo.exceptions.ShipmentException;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Location startLocation;
    @Enumerated(EnumType.STRING)
    private Location endLocation;
    private double maxCapacity;
    @ManyToMany
    private List<Cargo> cargoList = new ArrayList<>();
    private boolean arrived;


    public Shipment() {
    }

    public Shipment(Location startLocation, Location endLocation, double maxCapacity) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.maxCapacity = maxCapacity;
    }

    public double totalWeight() {
       return cargoList.stream().mapToDouble(Cargo::getWeight).sum();
    }

    public void addCargo(Cargo cargo) {
        if (arrived) {
            throw new ShipmentException("Shipment already arrived.");
        }
        if (cargo.getCurrentLocation() != startLocation) {
            throw new ShipmentException("Cargo at wrong location.");
        }
        if (cargo.getCargoStatus() == CargoStatus.DELIVERED) {
            throw new ShipmentException("Cargo already delivered.");
        }
        if (totalWeight() + cargo.getWeight() > maxCapacity) {
            throw new ShipmentException("Shipment capacity exceeded.");
        }
        cargoList.add(cargo);
        cargo.setCargoStatus(CargoStatus.MOVING);
    }

    public void setArrived() {
        if (arrived) {
            throw new ShipmentException("Shipment already arrived.");
        }
        cargoList.forEach(c -> c.arrive(endLocation));
        arrived = true;
    }

    public boolean isArrived() {
        return arrived;
    }

    public double getCapacityUtilization() {
        return totalWeight() / maxCapacity;
    }

    public Location getEndLocation() {
        return endLocation;
    }
}
