package org.example.reportsworskhopgft.eventlog.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.reportsworskhopgft.eventlog.infrastructure.EventLogService;
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
    private EventLogService eventLogService;

    @Spy
    private ObjectMapper objectMapper = new ObjectMapper();

    private TransportEventConsumer consumer;

    @BeforeEach
    void setUp() {
        // Inyectamos el servicio de tus compañeros y el mapper
        consumer = new TransportEventConsumer(eventLogService, objectMapper);
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

        // Verificamos que llamamos al método save() de tus compañeros con los parámetros exactos
        verify(eventLogService, times(1)).save(
                eq("TRUCK_REGISTERED"), // eventType
                eq("TRANSPORT"),        // sourceService
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
                .hasMessageContaining("Error deserializando el evento");
    }
}