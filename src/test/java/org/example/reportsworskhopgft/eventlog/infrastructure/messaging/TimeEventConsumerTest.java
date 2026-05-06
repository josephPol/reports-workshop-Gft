package org.example.reportsworskhopgft.eventlog.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.example.reportsworskhopgft.eventlog.application.EventLogService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TimeEventConsumerTest {

    @Mock
    private EventLogService eventLogService;

    @Spy
    private ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    private TimeEventConsumer consumer;

    @Test
    void should_process_time_advanced_event() {
        String validJson = """
                {
                  "simulationDay": 3,
                  "occurredAt": "2024-01-01T00:00:00Z"
                }
                """;

        consumer.onTimeAdvanced(validJson);

        verify(eventLogService, times(1)).save(
                eq("time.advanced.v1"),
                eq("time"),
                any(String.class),
                eq(3),
                eq("2024-01-01T00:00:00Z")
        );
    }

    @Test
    void should_throw_exception_when_message_is_invalid() {
        String invalidJson = "esto-no-es-json";

        assertThatThrownBy(() -> consumer.onTimeAdvanced(invalidJson))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error procesando time.advanced.v1");
    }
}