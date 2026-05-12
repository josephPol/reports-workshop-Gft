package org.example.reportsworskhopgft.eventlog.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.reportsworskhopgft.eventlog.application.impl.EventLogServiceImpl;
import org.example.reportsworskhopgft.eventlog.domain.EventType;
import org.example.reportsworskhopgft.eventlog.domain.SourceService;
import org.example.reportsworskhopgft.eventlog.infrastructure.messaging.time.TimeAdvancedMessage;
import org.example.reportsworskhopgft.eventlog.infrastructure.messaging.time.TimeEventConsumer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TimeEventConsumerTest {

    @Mock
    private EventLogServiceImpl eventLogServiceImpl;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private TimeEventConsumer consumer;

    @Test
    void should_process_time_advanced_event() throws Exception {
        TimeAdvancedMessage event = new TimeAdvancedMessage(3, "2024-01-01T00:00:00Z");

        when(objectMapper.writeValueAsString(event)).thenReturn(event.toString());

        consumer.onTimeAdvanced(event);

        verify(eventLogServiceImpl).save(
                eq(EventType.TIME_ADVANCED),
                eq(SourceService.TIME),
                eq(event.toString()),
                eq(3),
                eq("2024-01-01T00:00:00Z")
        );
    }

    @Test
    void should_throw_exception_when_processing_fails() throws Exception {
        TimeAdvancedMessage event = new TimeAdvancedMessage(5, "2024-02-01T10:00:00Z");

        when(objectMapper.writeValueAsString(event)).thenReturn(event.toString());

        doThrow(new RuntimeException("boom")).when(eventLogServiceImpl).save(
                eq(EventType.TIME_ADVANCED),
                eq(SourceService.TIME),
                eq(event.toString()),
                eq(5),
                eq("2024-02-01T10:00:00Z")
        );

        assertThatThrownBy(() -> consumer.onTimeAdvanced(event))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error processing time.advanced.v1");
    }
}
