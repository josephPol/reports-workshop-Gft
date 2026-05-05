package org.example.reportsworskhopgft.eventlog.infrastructure.messaging;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class TimeAdvancedMessageTest {

    @Test
    void should_create_message_with_all_fields() {
        TimeAdvancedMessage message = new TimeAdvancedMessage(3, "2024-01-01T00:00:00Z");

        assertThat(message.simulationDay()).isEqualTo(3);
        assertThat(message.occurredAt()).isEqualTo("2024-01-01T00:00:00Z");
    }

    @Test
    void should_convert_to_payload_string() {
        TimeAdvancedMessage message = new TimeAdvancedMessage(3, "2024-01-01T00:00:00Z");

        assertThat(message.toPayload()).contains("simulationDay");
        assertThat(message.toPayload()).contains("3");
    }
}