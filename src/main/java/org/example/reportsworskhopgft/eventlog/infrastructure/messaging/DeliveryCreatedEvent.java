package org.example.reportsworskhopgft.eventlog.infrastructure.messaging;

public record DeliveryCreatedEvent(
        String deliveryId,
        String truckId,
        int simulationDay,
        String timestamp
) {}