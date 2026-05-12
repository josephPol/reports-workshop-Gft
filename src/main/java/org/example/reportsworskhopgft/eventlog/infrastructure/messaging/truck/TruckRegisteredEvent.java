package org.example.reportsworskhopgft.eventlog.infrastructure.messaging.truck;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;

public record TruckRegisteredEvent(
        @JsonProperty("truckId")   UUID truckId,
        @JsonProperty("name")      String name,
        @JsonProperty("position")  Position position,
        @JsonProperty("capacity")  int capacity,
        @JsonProperty("timestamp") int timestamp
) {
    public record Position(
            @JsonProperty("x") int x,
            @JsonProperty("y") int y
    ) {}
}
