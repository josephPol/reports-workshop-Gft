package org.example.reportsworskhopgft.eventlog.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.reportsworskhopgft.eventlog.domain.EventType;
import org.example.reportsworskhopgft.eventlog.domain.SourceService;
import org.example.reportsworskhopgft.eventlog.application.EventLogServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TransportEventConsumerTest {

    @Mock
    private EventLogServiceImpl eventLogServiceImpl;

    @Spy
    private ObjectMapper objectMapper = new ObjectMapper();

    private TransportEventConsumer consumer;

    @BeforeEach
    void setUp() {
        consumer = new TransportEventConsumer(eventLogServiceImpl, objectMapper);
    }

    @Test
    void shouldProcessTruckRegisteredEvent() {
        String jsonMessage = """
            {
              "truckId": "123e4567-e89b-12d3-a456-426614174000",
              "name": "Camión Alpha",
              "position": { "x": 10, "y": 20 },
              "capacity": 50,
              "timestamp": 1
            }
            """;

        consumer.onTruckRegistered(jsonMessage);


        verify(eventLogServiceImpl, times(1)).save(
                eq(EventType.TRUCK_REGISTERED), // eventType
                eq(SourceService.TRANSPORT),        // sourceService
                eq(jsonMessage),        // payload (le pasamos el JSON intacto)
                eq(1),                  // simulationDay (sacado del timestamp)
                anyString()             // occurredAt
        );
    }

    @Test
    void shouldThrowExceptionWhenMessageIsInvalid() {
        String invalidMessage = "esto-no-es-json";

        assertThatThrownBy(() -> consumer.onTruckRegistered(invalidMessage))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error deserializing RabbitMQ event");
    }
}