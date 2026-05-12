package org.example.reportsworskhopgft.eventlog.infrastructure.messaging.factory;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProductionRecipeRegisteredMessage(
        @JsonProperty("simulationDay") int simulationDay,
        @JsonProperty("occurredAt")    String occurredAt
) {}