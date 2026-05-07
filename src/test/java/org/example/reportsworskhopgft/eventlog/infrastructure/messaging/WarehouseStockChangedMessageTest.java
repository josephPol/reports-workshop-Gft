package org.example.reportsworskhopgft.eventlog.infrastructure.messaging;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class WarehouseStockChangedMessageTest {

    @Test
    void should_create_message_with_all_fields() {
        WarehouseStockChangedMessage message = new WarehouseStockChangedMessage("abc-123", 50, "INCREASE");

        assertThat(message.productId()).isEqualTo("abc-123");
        assertThat(message.quantity()).isEqualTo(50);
        assertThat(message.type()).isEqualTo("INCREASE");
    }
}