package org.example.reportsworskhopgft.eventlog.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.reportsworskhopgft.eventlog.application.impl.EventLogServiceImpl;
import org.example.reportsworskhopgft.eventlog.domain.EventType;
import org.example.reportsworskhopgft.eventlog.domain.SourceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ProductionOrderCompletedConsumerTest {

    private EventLogServiceImpl eventLogService;
    private ProductionEventConsumer consumer;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        eventLogService = mock(EventLogServiceImpl.class);
        consumer = new ProductionEventConsumer(eventLogService, objectMapper);
    }

    @Test
    void should_save_event_log_when_production_order_completed_message_is_received() {
        String message = """
                {
                  "orderId":       "order-1",
                  "factoryId":     "factory-1",
                  "simulationDay": 8,
                  "occurredAt":    "2026-05-06T14:00:00"
                }
                """;

        consumer.onProductionOrderCompleted(message);

        verify(eventLogService).save(
                EventType.PRODUCTION_ORDER_COMPLETED,
                SourceService.FACTORY,
                message,
                8,
                "2026-05-06T14:00:00"
        );
    }

    @Test
    void should_throw_runtime_exception_when_completed_message_is_malformed() {
        String malformedMessage = "{ this is not valid json }";

        assertThrows(RuntimeException.class,
                () -> consumer.onProductionOrderCompleted(malformedMessage));

        verifyNoInteractions(eventLogService);
    }
}