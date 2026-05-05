package org.example.reportsworskhopgft.eventlog.application;

import java.util.UUID;

public record TruckRegisteredEvent(
        UUID truckId,
        String name,
        Position position,
        int capacity,
        int timestamp
) {
    // Record que representa la posición del camión.
    public record Position(int x, int y) {}
}