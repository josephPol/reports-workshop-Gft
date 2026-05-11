package org.example.reportsworskhopgft.eventlog.infrastructure.messaging.time;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TimeAdvancedMessage(
        @JsonProperty("daysAdvanced") int simulationDay,
        @JsonProperty("occurredAt") String occurredAt
) {

}