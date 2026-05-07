package org.example.reportsworskhopgft.eventlog.infrastructure.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.reportsworskhopgft.eventlog.domain.EventType;
import org.example.reportsworskhopgft.eventlog.domain.SourceService;
import org.example.reportsworskhopgft.eventlog.application.impl.EventLogServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TimeEventConsumerTest {

    @Mock
    private EventLogServiceImpl eventLogServiceImpl;

    @Spy
    private ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    private TimeEventConsumer consumer;

    @Test
    void should_process_time_advanced_event_with_normal_json() {
        String validJson = """
                {
                  "simulationDay": 3,
                  "occurredAt": "2024-01-01T00:00:00Z"
                }
                """;

        consumer.onTimeAdvanced(validJson);

        verify(eventLogServiceImpl).save(
                eq(EventType.TIME_ADVANCED),
                eq(SourceService.TIME),
                eq(validJson),
                eq(3),
                eq("2024-01-01T00:00:00Z")
        );
    }

    @Test
    void should_process_time_advanced_event_with_escaped_json_string() {
        String rawMessage = "\"{\\\"simulationDay\\\": 5, \\\"occurredAt\\\": \\\"2024-02-01T10:00:00Z\\\"}\"";
        String expectedUnescapedJson = "{\"simulationDay\": 5, \"occurredAt\": \"2024-02-01T10:00:00Z\"}";

        consumer.onTimeAdvanced(rawMessage);

        verify(eventLogServiceImpl).save(
                eq(EventType.TIME_ADVANCED),
                eq(SourceService.TIME),
                eq(expectedUnescapedJson),
                eq(5),
                eq("2024-02-01T10:00:00Z")
        );
    }

    @Test
    void should_throw_exception_when_message_is_invalid() {

        String invalidJson = "not-a-json-structure";

        assertThatThrownBy(() -> consumer.onTimeAdvanced(invalidJson))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error processing time.advanced.v1");

        verifyNoInteractions(eventLogServiceImpl);
    }
}