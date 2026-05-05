package org.example.reportsworskhopgft.eventlog.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.reportsworskhopgft.eventlog.application.EventLogService;
import org.example.reportsworskhopgft.eventlog.application.TruckRegisteredEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TransportEventConsumerTest {

    @Mock
    private EventLogService eventLogService;


    @Spy
    private ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    private TransportEventConsumer consumer;

    @Test
    void shouldProcessTruckRegisteredEvent() {

        String fakeJsonMessage = """
            {
              "truckId": "123e4567-e89b-12d3-a456-426614174000",
              "name": "Camión Alpha",
              "position": { "x": 10, "y": 20 },
              "capacity": 50,
              "timestamp": 1
            }
            """;


        consumer.onTruckRegistered(fakeJsonMessage);


        verify(eventLogService, times(1)).logEvent(any(TruckRegisteredEvent.class));
    }
    @Test
    void shouldThrowRuntimeExceptionWhenMessageIsInvalid() {
        String invalidJson = "esto-no-es-json";
        assertThatThrownBy(() -> consumer.onTruckRegistered(invalidJson))
        .isInstanceOf(RuntimeException.class)
        .hasMessageContaining("Error deserializando el evento de RabbitMQ");
    }
}