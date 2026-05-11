package org.example.reportsworskhopgft.eventlog.infrastructure.messaging.truck;

public record DeliveryCompletedEvent(
        String deliveryId,
        String truckId,
        int simulationDay,
        String timestamp
) {}