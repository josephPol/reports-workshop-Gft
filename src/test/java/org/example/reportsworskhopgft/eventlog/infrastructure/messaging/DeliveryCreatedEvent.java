package org.example.reportsworskhopgft.eventlog.infrastructure.messaging;

import org.example.reportsworskhopgft.eventlog.infrastructure.messaging.truck.DeliveryCreatedEvent;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DeliveryCreatedEventTest {

    @Test
    void shouldCreateDeliveryCreatedEvent() {
        DeliveryCreatedEvent event = new DeliveryCreatedEvent("DELIVERY_CREATED", "2024-06-01T12:00:00Z", 1, "product-456");

        assertNotNull(event);
    }
}