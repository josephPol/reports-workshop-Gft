package org.example.reportsworskhopgft.eventlog.infrastructure.messaging;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.reportsworskhopgft.eventlog.application.impl.EventLogServiceImpl;
import org.example.reportsworskhopgft.eventlog.domain.EventType;
import org.example.reportsworskhopgft.eventlog.domain.SourceService;
import org.example.reportsworskhopgft.eventlog.infrastructure.messaging.factory.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductionEventConsumerTest {

    @Mock private EventLogServiceImpl eventLogService;

    @Mock private ObjectMapper objectMapper;

    @InjectMocks private ProductionEventConsumer consumer;

    private final String DATE = "2026-05-12T10:00:00";
    private final String JSON_PAYLOAD = "{\"status\":\"serialized\"}";

    @BeforeEach
    void setUp() throws JsonProcessingException {
        // Comportamiento por defecto para que todos los tests de éxito funcionen
        lenient().when(objectMapper.writeValueAsString(any())).thenReturn(JSON_PAYLOAD);
    }

    @Test
    void should_save_on_ProductionOrderCreated() {
        var event = new ProductionOrderCreatedMessage("O1", "F1", "P1", 10, 5, DATE);

        consumer.onProductionOrderCreated(event);

        verify(eventLogService)
                .save(
                        EventType.PRODUCTION_ORDER_CREATED,
                        SourceService.FACTORY,
                        JSON_PAYLOAD,
                        5,
                        DATE);
    }

    @Test
    void should_save_on_ProductionOrderStarted() {
        // Ajustado a 4 argumentos según image_d183d7.png
        var event = new ProductionOrderStartedMessage("O1", "F1", 5, DATE);

        consumer.onProductionOrderStarted(event);

        verify(eventLogService)
                .save(
                        EventType.PRODUCTION_ORDER_STARTED,
                        SourceService.FACTORY,
                        JSON_PAYLOAD,
                        5,
                        DATE);
    }

    @Test
    void should_save_on_ProductionOrderCompleted() {
        var event = new ProductionOrderCompletedMessage("O1", "F1", 5, DATE);

        consumer.onProductionOrderCompleted(event);

        verify(eventLogService)
                .save(
                        EventType.PRODUCTION_ORDER_COMPLETED,
                        SourceService.FACTORY,
                        JSON_PAYLOAD,
                        5,
                        DATE);
    }

    @Test
    void should_save_on_ProductionOrderBlocked() {
        var event = new ProductionOrderBlockedMessage(5, DATE);

        consumer.onProductionOrderBlocked(event);

        verify(eventLogService)
                .save(
                        EventType.PRODUCTION_ORDER_BLOCKED,
                        SourceService.FACTORY,
                        JSON_PAYLOAD,
                        5,
                        DATE);
    }

    @Test
    void should_save_on_RecipeRegistered() {
        var event = new ProductionRecipeRegisteredMessage(5, DATE);

        consumer.onRecipeRegistered(event);

        verify(eventLogService)
                .save(
                        EventType.PRODUCTION_RECIPE_REGISTERED,
                        SourceService.FACTORY,
                        JSON_PAYLOAD,
                        5,
                        DATE);
    }

    @Test
    void should_save_on_FactoryRegistered() {
        var event = new ProductionFactoryRegisteredMessage(5, DATE);

        consumer.onFactoryRegistered(event);

        verify(eventLogService)
                .save(
                        EventType.PRODUCTION_FACTORY_REGISTERED,
                        SourceService.FACTORY,
                        JSON_PAYLOAD,
                        5,
                        DATE);
    }

    // --- TESTS PARA COBERTURA DE EXCEPCIONES (Bloques Catch) ---

    @Test
    void should_throw_exception_when_onProductionOrderCreated_fails()
            throws JsonProcessingException {
        when(objectMapper.writeValueAsString(any())).thenThrow(new RuntimeException("JSON Error"));
        var event = new ProductionOrderCreatedMessage("O1", "F1", "P1", 10, 5, DATE);

        assertThrows(RuntimeException.class, () -> consumer.onProductionOrderCreated(event));
    }

    @Test
    void should_throw_exception_when_onProductionOrderStarted_fails()
            throws JsonProcessingException {
        when(objectMapper.writeValueAsString(any())).thenThrow(new RuntimeException("JSON Error"));
        var event = new ProductionOrderStartedMessage("O1", "F1", 5, DATE);

        assertThrows(RuntimeException.class, () -> consumer.onProductionOrderStarted(event));
    }

    @Test
    void should_throw_exception_when_onProductionOrderCompleted_fails()
            throws JsonProcessingException {
        when(objectMapper.writeValueAsString(any())).thenThrow(new RuntimeException("JSON Error"));
        var event = new ProductionOrderCompletedMessage("O1", "F1", 5, DATE);

        assertThrows(RuntimeException.class, () -> consumer.onProductionOrderCompleted(event));
    }

    @Test
    void should_throw_exception_when_onProductionOrderBlocked_fails()
            throws JsonProcessingException {
        when(objectMapper.writeValueAsString(any())).thenThrow(new RuntimeException("JSON Error"));
        var event = new ProductionOrderBlockedMessage(5, DATE);

        assertThrows(RuntimeException.class, () -> consumer.onProductionOrderBlocked(event));
    }

    @Test
    void should_throw_exception_when_onRecipeRegistered_fails() throws JsonProcessingException {
        when(objectMapper.writeValueAsString(any())).thenThrow(new RuntimeException("JSON Error"));
        var event = new ProductionRecipeRegisteredMessage(5, DATE);

        assertThrows(RuntimeException.class, () -> consumer.onRecipeRegistered(event));
    }

    @Test
    void should_throw_exception_when_onFactoryRegistered_fails() throws JsonProcessingException {
        when(objectMapper.writeValueAsString(any())).thenThrow(new RuntimeException("JSON Error"));
        var event = new ProductionFactoryRegisteredMessage(5, DATE);

        assertThrows(RuntimeException.class, () -> consumer.onFactoryRegistered(event));
    }
}
