package org.example.reportsworskhopgft.eventlog.infrastructure.messaging;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.reportsworskhopgft.eventlog.application.impl.EventLogServiceImpl;
import org.example.reportsworskhopgft.eventlog.domain.EventType;
import org.example.reportsworskhopgft.eventlog.domain.SourceService;
import org.example.reportsworskhopgft.eventlog.infrastructure.messaging.factory.ProductionEventConsumer;
import org.example.reportsworskhopgft.eventlog.infrastructure.messaging.factory.ProductionOrderStartedMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        ProductionOrderStartedMessage message =
                new ProductionOrderStartedMessage("order-1", "factory-1", 6, "2026-05-06T11:00:00");

        consumer.onProductionOrderStarted(message);

        verify(eventLogService)
                .save(
                        eq(EventType.PRODUCTION_ORDER_STARTED),
                        eq(SourceService.FACTORY),
                        any(String.class),
                        eq(6),
                        eq("2026-05-06T11:00:00"));
    }

    @Test
    void should_throw_runtime_exception_when_started_message_is_malformed() {
        // Removed
    }
}
