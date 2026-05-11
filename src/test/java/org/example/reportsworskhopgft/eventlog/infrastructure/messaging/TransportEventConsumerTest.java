package org.example.reportsworskhopgft.eventlog.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.reportsworskhopgft.eventlog.application.impl.EventLogServiceImpl;
import org.example.reportsworskhopgft.eventlog.domain.EventType;
import org.example.reportsworskhopgft.eventlog.domain.SourceService;
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
                .isInstanceOf(RuntimeException.class).hasMessageContaining("Error processing truck position event");

    }

    @Test
    void shouldProcessTruckStatusChangedEventAndSaveLog() throws Exception {

        String validJsonMessage = """
                {
                  "truckId": "T-456",
                  "status": "ON_ROUTE",
                  "simulationDay": 3,
                  "timestamp": "2026-05-11T10:04:33"
                }
                """;


        consumer.onTruckStatusChanged(validJsonMessage);

        org.mockito.Mockito.verify(eventLogServiceImpl).save(
                org.mockito.ArgumentMatchers.eq(EventType.TRUCK_STATUS_CHANGED),
                org.mockito.ArgumentMatchers.eq(SourceService.TRANSPORT),
                org.mockito.ArgumentMatchers.anyString(),
                org.mockito.ArgumentMatchers.eq(3),
                org.mockito.ArgumentMatchers.eq("2026-05-11T10:04:33")
        );
    }

    @Test
    void shouldThrowExceptionWhenStatusMessageIsInvalid() {

        String invalidMessage = "not-valid-json";

        RuntimeException exception = org.junit.jupiter.api.Assertions.assertThrows(
                RuntimeException.class,
                () -> consumer.onTruckStatusChanged(invalidMessage)
        );


        org.junit.jupiter.api.Assertions.assertTrue(
                exception.getMessage().contains("Error processing truck status event")
        );
    }
    @Test
    void shouldProcessDeliveryCreatedEventAndSaveLog() throws Exception {
        String validJsonMessage = """
                {
                  "deliveryId": "DEL-999",
                  "truckId": "T-123",
                  "simulationDay": 4,
                  "timestamp": "2026-05-12T08:30:00"
                }
                """;


        consumer.onDeliveryCreated(validJsonMessage);


        org.mockito.Mockito.verify(eventLogServiceImpl).save(
                org.mockito.ArgumentMatchers.eq(EventType.DELIVERY_CREATED),
                org.mockito.ArgumentMatchers.eq(SourceService.TRANSPORT),
                org.mockito.ArgumentMatchers.anyString(),
                org.mockito.ArgumentMatchers.eq(4),
                org.mockito.ArgumentMatchers.eq("2026-05-12T08:30:00")
        );
    }

    @Test
    void shouldThrowExceptionWhenDeliveryMessageIsInvalid() {

        String invalidMessage = "{ not-valid-json }";


        RuntimeException exception = org.junit.jupiter.api.Assertions.assertThrows(
                RuntimeException.class,
                () -> consumer.onDeliveryCreated(invalidMessage)
        );


        org.junit.jupiter.api.Assertions.assertTrue(
                exception.getMessage().contains("Error processing delivery created event")
        );
    }
    @Test
    void shouldProcessDeliveryCompletedEventAndSaveTwoLogs() throws Exception {
        // Arrange
        String validJsonMessage = """
                {
                  "deliveryId": "DEL-999",
                  "truckId": "T-123",
                  "simulationDay": 5,
                  "timestamp": "2026-05-13T10:00:00"
                }
                """;


        consumer.onDeliveryCompleted(validJsonMessage);

        org.mockito.Mockito.verify(eventLogServiceImpl).save(
                org.mockito.ArgumentMatchers.eq(EventType.DELIVERY_COMPLETED),
                org.mockito.ArgumentMatchers.eq(SourceService.TRANSPORT),
                org.mockito.ArgumentMatchers.anyString(),
                org.mockito.ArgumentMatchers.eq(5),
                org.mockito.ArgumentMatchers.eq("2026-05-13T10:00:00")
        );


        org.mockito.Mockito.verify(eventLogServiceImpl).save(
                org.mockito.ArgumentMatchers.eq(EventType.TRUCK_STATUS_CHANGED),
                org.mockito.ArgumentMatchers.eq(SourceService.TRANSPORT),
                org.mockito.ArgumentMatchers.anyString(),
                org.mockito.ArgumentMatchers.eq(5),
                org.mockito.ArgumentMatchers.eq("2026-05-13T10:00:00")
        );
    }

    @Test
    void shouldThrowExceptionWhenDeliveryCompletedMessageIsInvalid() {

        String invalidMessage = "not-valid-json";


        RuntimeException exception = org.junit.jupiter.api.Assertions.assertThrows(
                RuntimeException.class,
                () -> consumer.onDeliveryCompleted(invalidMessage)
        );

        org.junit.jupiter.api.Assertions.assertTrue(
                exception.getMessage().contains("Error processing delivery completed event")
        );
    }
}