package org.example.reportsworskhopgft.eventlog.infrastructure.messaging.truck;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.example.reportsworskhopgft.eventlog.application.impl.EventLogServiceImpl;
import org.example.reportsworskhopgft.eventlog.domain.EventType;
import org.example.reportsworskhopgft.eventlog.domain.SourceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

@ExtendWith(MockitoExtension.class)
class TransportEventConsumerTest {

    @Mock private EventLogServiceImpl eventLogServiceImpl;

    @Mock private ObjectMapper objectMapper;

    @InjectMocks private TransportEventConsumer consumer;

    private final String TIMESTAMP = "2026-05-12T10:00:00";
    private final String JSON_PAYLOAD = "{\"status\":\"ok\"}";
    private final int DAY = 5;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        // Por defecto, el mapper funciona bien para los tests de éxito
        // lenient().when(objectMapper.writeValueAsString(any())).thenReturn(JSON_PAYLOAD);
    }

    // --- TESTS DE FLUJO EXITOSO ---

    @Test
    void should_process_truck_registered_event_successfully() throws JsonProcessingException {

        var event =
                new TruckRegisteredEvent(
                        UUID.randomUUID(),
                        "Alpha",
                        new TruckRegisteredEvent.Position(1, 1),
                        100,
                        DAY);

        doReturn(JSON_PAYLOAD).when(objectMapper).writeValueAsString(event);

        consumer.onTruckRegistered(event);

        verify(eventLogServiceImpl)
                .save(
                        eq(EventType.TRUCK_REGISTERED),
                        eq(SourceService.TRANSPORT),
                        eq(JSON_PAYLOAD),
                        eq(DAY),
                        anyString());
    }

    @Test
    void should_process_truck_position_update_successfully() throws JsonProcessingException {
        var event =
                new TruckPositionUpdateEvent(
                        "T-1", new TruckPositionUpdateEvent.Position(0, 0), DAY, TIMESTAMP);

        when(objectMapper.writeValueAsString(event)).thenReturn(JSON_PAYLOAD);

        consumer.onTruckPositionUpdate(event);

        verify(eventLogServiceImpl)
                .save(
                        EventType.TRUCK_POSITION_UPDATED,
                        SourceService.TRANSPORT,
                        JSON_PAYLOAD,
                        DAY,
                        TIMESTAMP);
    }

    @Test
    void should_process_truck_status_changed_successfully() throws JsonProcessingException {
        var event = new TruckStatusChangedEvent("T-1", "AVAILABLE", DAY, TIMESTAMP);

        when(objectMapper.writeValueAsString(event)).thenReturn(JSON_PAYLOAD);

        consumer.onTruckStatusChanged(event);

        verify(eventLogServiceImpl)
                .save(
                        EventType.TRUCK_STATUS_CHANGED,
                        SourceService.TRANSPORT,
                        JSON_PAYLOAD,
                        DAY,
                        TIMESTAMP);
    }

    @Test
    void should_process_delivery_completed_successfully_and_save_twice()
            throws JsonProcessingException {
        var event = new DeliveryCompletedEvent("D-1", "T-1", DAY, TIMESTAMP);

        doReturn(JSON_PAYLOAD).when(objectMapper).writeValueAsString(event);

        consumer.onDeliveryCompleted(event);

        // Verifica que se guardan los dos eventos que pide tu lógica
        verify(eventLogServiceImpl)
                .save(
                        EventType.DELIVERY_COMPLETED,
                        SourceService.TRANSPORT,
                        JSON_PAYLOAD,
                        DAY,
                        TIMESTAMP);
        verify(eventLogServiceImpl)
                .save(
                        EventType.TRUCK_STATUS_CHANGED,
                        SourceService.TRANSPORT,
                        JSON_PAYLOAD,
                        DAY,
                        TIMESTAMP);
    }

    // --- TESTS DE COBERTURA DE EXCEPCIONES (BLOQUES CATCH) ---

    @Test
    void should_throw_exception_when_onTruckRegistered_fails() throws JsonProcessingException {
        var event = new TruckRegisteredEvent(UUID.randomUUID(), "Error", null, 0, DAY);

        when(objectMapper.writeValueAsString(eq(event)))
                .thenThrow(new RuntimeException("Jackson Error"));

        assertThatThrownBy(() -> consumer.onTruckRegistered(event))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Error deserializing RabbitMQ event");
    }

    @Test
    void should_throw_exception_when_onTruckRegistered_json_processing_fails()
            throws JsonProcessingException {
        var event = new TruckRegisteredEvent(UUID.randomUUID(), "Error", null, 0, DAY);

        when(objectMapper.writeValueAsString(eq(event)))
                .thenThrow(new com.fasterxml.jackson.databind.JsonMappingException("JSON Error"));

        assertThatThrownBy(() -> consumer.onTruckRegistered(event))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Error deserializing RabbitMQ event");
    }

    @Test
    void should_throw_exception_when_onTruckRegistered_data_access_fails()
            throws JsonProcessingException {
        var event = new TruckRegisteredEvent(UUID.randomUUID(), "Error", null, 0, DAY);

        doReturn(JSON_PAYLOAD).when(objectMapper).writeValueAsString(event);

        doThrow(new DataAccessException("DB Error") {})
                .when(eventLogServiceImpl)
                .save(
                        eq(EventType.TRUCK_REGISTERED),
                        eq(SourceService.TRANSPORT),
                        eq(JSON_PAYLOAD),
                        eq(event.timestamp()),
                        anyString());

        assertThatThrownBy(() -> consumer.onTruckRegistered(event))
                .isInstanceOf(DataAccessException.class)
                .hasMessage("DB Error");
    }

    @Test
    void should_throw_exception_when_onTruckPositionUpdate_fails() throws JsonProcessingException {
        var event = new TruckPositionUpdateEvent("T-1", null, DAY, TIMESTAMP);

        when(objectMapper.writeValueAsString(eq(event)))
                .thenThrow(new RuntimeException("Jackson Error"));

        assertThatThrownBy(() -> consumer.onTruckPositionUpdate(event))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Error deserializing RabbitMQ event");
    }

    @Test
    void should_throw_exception_when_onTruckStatusChanged_fails() throws JsonProcessingException {
        var event = new TruckStatusChangedEvent("T-1", "STATUS", DAY, TIMESTAMP);

        when(objectMapper.writeValueAsString(eq(event)))
                .thenThrow(new RuntimeException("Jackson Error"));

        assertThatThrownBy(() -> consumer.onTruckStatusChanged(event))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Error deserializing RabbitMQ event");
    }

    @Test
    void should_throw_exception_when_onDeliveryCompleted_fails() throws JsonProcessingException {
        var event = new DeliveryCompletedEvent("D-1", "T-1", DAY, TIMESTAMP);

        when(objectMapper.writeValueAsString(eq(event)))
                .thenThrow(new RuntimeException("Jackson Error"));

        assertThatThrownBy(() -> consumer.onDeliveryCompleted(event))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Error deserializing RabbitMQ event");
    }

    @Test
    void should_throw_exception_when_onTruckPositionUpdate_data_access_fails()
            throws JsonProcessingException {
        var event =
                new TruckPositionUpdateEvent(
                        "T-1", new TruckPositionUpdateEvent.Position(0, 0), DAY, TIMESTAMP);

        doReturn(JSON_PAYLOAD).when(objectMapper).writeValueAsString(event);

        doThrow(new DataAccessException("DB Error") {})
                .when(eventLogServiceImpl)
                .save(
                        eq(EventType.TRUCK_POSITION_UPDATED),
                        eq(SourceService.TRANSPORT),
                        eq(JSON_PAYLOAD),
                        eq(DAY),
                        eq(TIMESTAMP));

        assertThatThrownBy(() -> consumer.onTruckPositionUpdate(event))
                .isInstanceOf(DataAccessException.class)
                .hasMessage("DB Error");
    }

    @Test
    void should_throw_exception_when_onTruckStatusChanged_data_access_fails()
            throws JsonProcessingException {
        var event = new TruckStatusChangedEvent("T-1", "AVAILABLE", DAY, TIMESTAMP);

        doReturn(JSON_PAYLOAD).when(objectMapper).writeValueAsString(event);

        doThrow(new DataAccessException("DB Error") {})
                .when(eventLogServiceImpl)
                .save(
                        eq(EventType.TRUCK_STATUS_CHANGED),
                        eq(SourceService.TRANSPORT),
                        eq(JSON_PAYLOAD),
                        eq(DAY),
                        eq(TIMESTAMP));

        assertThatThrownBy(() -> consumer.onTruckStatusChanged(event))
                .isInstanceOf(DataAccessException.class)
                .hasMessage("DB Error");
    }

    @Test
    void should_throw_exception_when_onDeliveryCompleted_data_access_fails()
            throws JsonProcessingException {
        var event = new DeliveryCompletedEvent("D-1", "T-1", DAY, TIMESTAMP);

        doReturn(JSON_PAYLOAD).when(objectMapper).writeValueAsString(event);

        doThrow(new DataAccessException("DB Error") {})
                .when(eventLogServiceImpl)
                .save(
                        eq(EventType.DELIVERY_COMPLETED),
                        eq(SourceService.TRANSPORT),
                        eq(JSON_PAYLOAD),
                        eq(DAY),
                        eq(TIMESTAMP));

        assertThatThrownBy(() -> consumer.onDeliveryCompleted(event))
                .isInstanceOf(DataAccessException.class)
                .hasMessage("DB Error");
    }

    @Test
    void should_throw_exception_when_onTruckPositionUpdate_json_processing_fails()
            throws JsonProcessingException {
        var event = new TruckPositionUpdateEvent("T-1", null, DAY, TIMESTAMP);

        when(objectMapper.writeValueAsString(eq(event)))
                .thenThrow(new com.fasterxml.jackson.databind.JsonMappingException("JSON Error"));

        assertThatThrownBy(() -> consumer.onTruckPositionUpdate(event))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Error deserializing RabbitMQ event");
    }

    @Test
    void should_throw_exception_when_onTruckStatusChanged_json_processing_fails()
            throws JsonProcessingException {
        var event = new TruckStatusChangedEvent("T-1", "STATUS", DAY, TIMESTAMP);

        when(objectMapper.writeValueAsString(eq(event)))
                .thenThrow(new com.fasterxml.jackson.databind.JsonMappingException("JSON Error"));

        assertThatThrownBy(() -> consumer.onTruckStatusChanged(event))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Error deserializing RabbitMQ event");
    }

    @Test
    void should_throw_exception_when_onDeliveryCompleted_json_processing_fails()
            throws JsonProcessingException {
        var event = new DeliveryCompletedEvent("D-1", "T-1", DAY, TIMESTAMP);

        when(objectMapper.writeValueAsString(eq(event)))
                .thenThrow(new com.fasterxml.jackson.databind.JsonMappingException("JSON Error"));

        assertThatThrownBy(() -> consumer.onDeliveryCompleted(event))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Error deserializing RabbitMQ event");
    }
}
