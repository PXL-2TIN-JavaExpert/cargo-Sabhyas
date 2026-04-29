package be.pxl.cargo.api.response;

import be.pxl.cargo.domain.CargoStatus;

import java.util.Map;

public class CargoStatistics {
    private Map<CargoStatus, Long> statusCount;
    private String heaviestCargo;
    private double averageCargoWeight;
    private long countCargosAtWarehouseA;
    private double totalWeightDeliveredAtCityB;

    public void setStatusCount(Map<CargoStatus, Long> statusCount) {
        this.statusCount = statusCount;
    }

    public void setHeaviestCargo(String heaviestCargo) {
        this.heaviestCargo = heaviestCargo;
    }

    public void setAverageCargoWeight(double averageCargoWeight) {
        this.averageCargoWeight = averageCargoWeight;
    }


    public Map<CargoStatus, Long> getStatusCount() {
        return statusCount;
    }

    public String getHeaviestCargo() {
        return heaviestCargo;
    }

    public double getAverageCargoWeight() {
        return averageCargoWeight;
    }

    public void setCountCargosAtWarehouseA(long countCargosAtWarehouseA) {
        this.countCargosAtWarehouseA = countCargosAtWarehouseA;
    }

    public void setTotalWeightDeliveredAtCityB(double totalWeightDeliveredAtCityC) {
        this.totalWeightDeliveredAtCityB = totalWeightDeliveredAtCityC;
    }

    public long getCountCargosAtWarehouseA() {
        return countCargosAtWarehouseA;
    }

    public double getTotalWeightDeliveredAtCityB() {
        return totalWeightDeliveredAtCityB;
    }
}
