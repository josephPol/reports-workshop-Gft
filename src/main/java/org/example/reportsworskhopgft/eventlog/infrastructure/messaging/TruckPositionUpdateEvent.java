package org.example.reportsworskhopgft.eventlog.infrastructure.messaging;

public record TruckPositionUpdateEvent(
        String truckId,
        Position position,
        int simulationDay,
        String timestamp
) {

    public record Position(int x, int y) {}
}