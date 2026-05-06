package org.example.reportsworskhopgft.eventlog.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.reportsworskhopgft.eventlog.application.EventLogServiceImpl;
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
class WarehouseEventConsumerTest {

    @Mock
    private EventLogServiceImpl eventLogServiceImpl;

    @Spy
    private ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    private WarehouseEventConsumer consumer;

    @Test
    void should_process_warehouse_stock_changed_event() {
        String validJson = """
                {
                  "productId": "abc-123",
                  "quantity": 50,
                  "type": "INCREASE"
                }
                """;

        consumer.onWarehouseStockChanged(validJson);

        verify(eventLogServiceImpl, times(1)).save(
                eq(EventType.WAREHOUSE_STOCK_CHANGED),
                eq(SourceService.WAREHOUSE),
                any(String.class),
                eq(0),
                eq("")
        );
    }

    @Test
    void should_throw_exception_when_message_is_invalid() {
        String invalidJson = "esto-no-es-json";

        assertThatThrownBy(() -> consumer.onWarehouseStockChanged(invalidJson))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error procesando warehouse.stock.changed.v1");
    }

    @Test
    void should_process_replenishment_requested_event() {
        String validJson = """
                {
                  "productId": "abc-123",
                  "quantity": 50,
                  "type": "REPLENISHMENT"
                }
                """;

        consumer.onReplenishmentRequested(validJson);

        verify(eventLogServiceImpl, times(1)).save(
                eq(EventType.REPLENISHMENT_REQUESTED),
                eq(SourceService.WAREHOUSE),
                any(String.class),
                eq(0),
                eq("")
        );
    }

    @Test
    void should_throw_exception_when_replenishment_message_is_invalid() {
        String invalidJson = "esto-no-es-json";

        assertThatThrownBy(() -> consumer.onReplenishmentRequested(invalidJson))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error procesando replenishment.requested.v1");
    }
}