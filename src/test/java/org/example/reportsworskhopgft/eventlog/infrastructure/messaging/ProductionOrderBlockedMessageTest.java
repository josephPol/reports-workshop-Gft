package org.example.reportsworskhopgft.eventlog.infrastructure.messaging;

import org.example.reportsworskhopgft.eventlog.infrastructure.messaging.factory.ProductionOrderBlockedMessage;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductionOrderBlockedMessageTest {

    @Test
    void shouldCreateProductionOrderBlockedMessage() {
        ProductionOrderBlockedMessage message = new ProductionOrderBlockedMessage(5, "2026-05-07T10:00:00");

        assertEquals(5, message.simulationDay());
        assertEquals("2026-05-07T10:00:00", message.occurredAt());
    }
}