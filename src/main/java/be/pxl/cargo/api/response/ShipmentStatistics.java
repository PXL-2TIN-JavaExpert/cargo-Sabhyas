package be.pxl.cargo.api.response;

import be.pxl.cargo.domain.Location;

import java.util.List;

public class ShipmentStatistics {

    private List<Location> distinctEndLocations;
    private long numberOfShipments;
    private long arrived;
    private double avgCapacityUtilization;

    public void setDistinctEndLocations(List<Location> distinctEndLocations) {
        this.distinctEndLocations = distinctEndLocations;
    }

    public void setNumberOfShipments(long numberOfShipments) {
        this.numberOfShipments = numberOfShipments;
    }

    public void setArrived(long arrived) {
        this.arrived = arrived;
    }

    public void setAvgCapacityUtilization(double avgCapacityUtilization) {
        this.avgCapacityUtilization = avgCapacityUtilization;
    }

    public List<Location> getDistinctEndLocations() {
        return distinctEndLocations;
    }

    public long getNumberOfShipments() {
        return numberOfShipments;
    }

    public long getArrived() {
        return arrived;
    }

    public double getAvgCapacityUtilization() {
        return avgCapacityUtilization;
    }
}
