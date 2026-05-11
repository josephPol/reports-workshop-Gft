package org.example.reportsworskhopgft.eventlog.infrastructure.messaging.truck;

import java.util.UUID;

public record TruckRegisteredEvent(
        UUID truckId,
        String name,
        Position position,
        int capacity,
        int timestamp
) {
    public record Position(int x, int y) {}
}
