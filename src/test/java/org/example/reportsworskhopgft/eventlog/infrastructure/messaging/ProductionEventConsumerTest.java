package org.example.reportsworskhopgft.eventlog.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.reportsworskhopgft.eventlog.application.impl.EventLogServiceImpl;
import org.example.reportsworskhopgft.eventlog.domain.EventType;
import org.example.reportsworskhopgft.eventlog.domain.SourceService;
import org.example.reportsworskhopgft.eventlog.infrastructure.messaging.factory.ProductionEventConsumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ProductionEventConsumerTest {

    private EventLogServiceImpl eventLogService;
    private ProductionEventConsumer consumer;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        eventLogService = mock(EventLogServiceImpl.class);
        consumer = new ProductionEventConsumer(eventLogService, objectMapper);
    }

    @Test
    void should_save_event_log_when_production_order_created_message_is_received() {
        String message = """
                {
                  "orderId":      "order-1",
                  "factoryId":    "factory-1",
                  "productId":    "product-1",
                  "quantity":     3,
                  "simulationDay": 5,
                  "occurredAt":   "2026-05-06T10:00:00"
                }
                """;

        consumer.onProductionOrderCreated(message);

        verify(eventLogService).save(
                EventType.PRODUCTION_ORDER_CREATED,
                SourceService.FACTORY,
                message,
                5,
                "2026-05-06T10:00:00"
        );
    }

    @Test
    void should_throw_runtime_exception_when_created_message_is_malformed() {
        String malformedMessage = "{ this is not valid json }";

        assertThrows(RuntimeException.class,
                () -> consumer.onProductionOrderCreated(malformedMessage));

        verifyNoInteractions(eventLogService);
    }

    @Test
    void should_save_event_log_when_production_order_blocked_message_is_received() {
        String message = """
                {
                  "simulationDay": 7,
                  "occurredAt":   "2026-05-06T13:00:00"
                }
                """;

        consumer.onProductionOrderBlocked(message);

        verify(eventLogService).save(
                EventType.PRODUCTION_ORDER_BLOCKED,
                SourceService.FACTORY,
                message,
                7,
                "2026-05-06T13:00:00"
        );
    }

    @Test
    void should_throw_runtime_exception_when_blocked_message_is_malformed() {
        String malformedMessage = "{ this is not valid json }";

        assertThrows(RuntimeException.class,
                () -> consumer.onProductionOrderBlocked(malformedMessage));

        verifyNoInteractions(eventLogService);
    }

    @Test
    void should_save_event_log_when_recipe_registered_message_is_received() {
        String message = """
                {
                  "simulationDay": 9,
                  "occurredAt":   "2026-05-06T15:00:00"
                }
                """;

        consumer.onRecipeRegistered(message);

        verify(eventLogService).save(
                EventType.PRODUCTION_RECIPE_REGISTERED,
                SourceService.FACTORY,
                message,
                9,
                "2026-05-06T15:00:00"
        );
    }

    @Test
    void should_throw_runtime_exception_when_recipe_registered_message_is_malformed() {
        String malformedMessage = "{ this is not valid json }";

        assertThrows(RuntimeException.class,
                () -> consumer.onRecipeRegistered(malformedMessage));

        verifyNoInteractions(eventLogService);
    }

    @Test
    void should_save_event_log_when_factory_registered_message_is_received() {
        String message = """
                {
                  "simulationDay": 10,
                  "occurredAt":   "2026-05-06T16:00:00"
                }
                """;

        consumer.onFactoryRegistered(message);

        verify(eventLogService).save(
                EventType.PRODUCTION_FACTORY_REGISTERED,
                SourceService.FACTORY,
                message,
                10,
                "2026-05-06T16:00:00"
        );
    }

    @Test
    void should_throw_runtime_exception_when_factory_registered_message_is_malformed() {
        String malformedMessage = "{ this is not valid json }";

        assertThrows(RuntimeException.class,
                () -> consumer.onFactoryRegistered(malformedMessage));

        verifyNoInteractions(eventLogService);
    }
}
