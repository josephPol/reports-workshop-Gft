package org.example.reportsworskhopgft.eventlog.infrastructure.messaging;

public record TruckStatusChangedEvent(
        String truckId,
        String status,
        int simulationDay,
        String timestamp
) {}