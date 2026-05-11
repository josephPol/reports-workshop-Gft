package org.example.reportsworskhopgft.eventlog.infrastructure.messaging.truck;

public record DeliveryCreatedEvent(
        String deliveryId,
        String truckId,
        int simulationDay,
        String timestamp
) {}