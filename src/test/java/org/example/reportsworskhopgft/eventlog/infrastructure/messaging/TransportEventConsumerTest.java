package org.example.reportsworskhopgft.eventlog.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.reportsworskhopgft.eventlog.domain.EventType;
import org.example.reportsworskhopgft.eventlog.domain.SourceService;
import org.example.reportsworskhopgft.eventlog.application.EventLogServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TransportEventConsumerTest {

    @Mock
    private EventLogServiceImpl eventLogServiceImpl;

    @Spy
    private ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    private TransportEventConsumer consumer;


    @Test
    void should_process_truck_registered_event() {
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
                eq(EventType.TRUCK_REGISTERED),
                eq(SourceService.TRANSPORT),
                any(String.class),
                eq(1),
                any(String.class)
        );
    }

    @Test
    void should_throw_exception_when_truck_registered_message_is_invalid() {
        String invalidMessage = "not-valid-json";

        assertThatThrownBy(() -> consumer.onTruckRegistered(invalidMessage))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error deserializing RabbitMQ event");
    }

    @Test
    void should_process_truck_position_update_event() {
        String validJsonMessage = """
            {
              "truckId": "T-123",
              "position": { "x": 10, "y": 20 },
              "simulationDay": 2,
              "timestamp": "2026-05-06T11:00:00"
            }
            """;

        consumer.onTruckPositionUpdate(validJsonMessage);

        verify(eventLogServiceImpl, times(1)).save(
                eq(EventType.TRUCK_POSITION_UPDATED),
                eq(SourceService.TRANSPORT),
                any(String.class),
                eq(2),
                any(String.class)
        );
    }

    @Test
    void should_throw_exception_when_truck_position_message_is_invalid() {
        String invalidMessage = "not-valid-json";

        assertThatThrownBy(() -> consumer.onTruckPositionUpdate(invalidMessage))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error processing truck position event");
    }
}