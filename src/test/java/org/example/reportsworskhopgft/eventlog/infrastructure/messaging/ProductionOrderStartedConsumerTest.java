package org.example.reportsworskhopgft.eventlog.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.reportsworskhopgft.eventlog.application.impl.EventLogServiceImpl;
import org.example.reportsworskhopgft.eventlog.domain.EventType;
import org.example.reportsworskhopgft.eventlog.domain.SourceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ProductionOrderStartedConsumerTest {

    private EventLogServiceImpl eventLogService;
    private ProductionEventConsumer consumer;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        eventLogService = mock(EventLogServiceImpl.class);
        consumer = new ProductionEventConsumer(eventLogService, objectMapper);
    }

    @Test
    void should_save_event_log_when_production_order_started_message_is_received() {
        String message = """
                {
                  "orderId":       "order-1",
                  "factoryId":     "factory-1",
                  "simulationDay": 6,
                  "occurredAt":    "2026-05-06T11:00:00"
                }
                """;

        consumer.onProductionOrderStarted(message);

        verify(eventLogService).save(
                EventType.PRODUCTION_ORDER_STARTED,
                SourceService.FACTORY,
                message,
                6,
                "2026-05-06T11:00:00"
        );
    }

    @Test
    void should_throw_runtime_exception_when_started_message_is_malformed() {
        String malformedMessage = "{ this is not valid json }";

        assertThrows(RuntimeException.class,
                () -> consumer.onProductionOrderStarted(malformedMessage));

        verifyNoInteractions(eventLogService);
    }
}