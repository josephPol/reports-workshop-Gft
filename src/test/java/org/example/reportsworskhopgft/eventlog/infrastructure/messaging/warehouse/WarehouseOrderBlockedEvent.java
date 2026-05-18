package org.example.reportsworskhopgft.eventlog.infrastructure.messaging.warehouse;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class WarehouseOrderBlockedEventTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void should_create_event_and_access_properties() {
        // Given
        UUID expectedId = UUID.randomUUID();

        // When
        WarehouseOrderBlockedEvent event = new WarehouseOrderBlockedEvent(expectedId);

        // Then
        assertThat(event.orderId()).isEqualTo(expectedId);
    }

    @Test
    void should_deserialize_valid_json_to_record() throws Exception {
        // Given
        String uuidString = "123e4567-e89b-12d3-a456-426614174000";
        String json =
                """
                {
                  "orderId": "%s"
                }
                """
                        .formatted(uuidString);

        // When
        WarehouseOrderBlockedEvent event =
                objectMapper.readValue(json, WarehouseOrderBlockedEvent.class);

        // Then
        assertThat(event).isNotNull();
        assertThat(event.orderId()).isEqualTo(UUID.fromString(uuidString));
    }
}
