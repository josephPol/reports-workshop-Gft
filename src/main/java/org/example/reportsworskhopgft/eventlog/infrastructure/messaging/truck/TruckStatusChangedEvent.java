package org.example.reportsworskhopgft.eventlog.infrastructure.messaging.truck;

public record TruckStatusChangedEvent(
        String truckId,
        String status,
        int simulationDay,
        String timestamp
) {}