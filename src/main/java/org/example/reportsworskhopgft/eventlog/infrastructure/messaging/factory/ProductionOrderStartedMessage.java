package org.example.reportsworskhopgft.eventlog.infrastructure.messaging.factory;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProductionOrderStartedMessage(
        @JsonProperty("orderId") String orderId,
        @JsonProperty("factoryId") String factoryId,
        @JsonProperty("simulationDay") int simulationDay,
        @JsonProperty("occurredAt") String occurredAt) {}
