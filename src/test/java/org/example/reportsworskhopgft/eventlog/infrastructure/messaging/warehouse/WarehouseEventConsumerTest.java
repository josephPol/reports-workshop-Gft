package org.example.reportsworskhopgft.eventlog.infrastructure.messaging.warehouse;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.reportsworskhopgft.eventlog.application.impl.EventLogServiceImpl;
import org.example.reportsworskhopgft.eventlog.domain.EventType;
import org.example.reportsworskhopgft.eventlog.domain.SourceService;
import org.example.reportsworskhopgft.eventlog.infrastructure.messaging.exception.EventDeserializationException;
import org.example.reportsworskhopgft.eventlog.infrastructure.messaging.exception.EventProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

@ExtendWith(MockitoExtension.class)
class WarehouseEventConsumerTest {

    @Mock private EventLogServiceImpl eventLogServiceImpl;

    @Spy private ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks private WarehouseEventConsumer consumer;

    @Test
    void should_process_warehouse_stock_changed_event() {
        String validJson =
                """
                {
                  "productId": "abc-123",
                  "quantity": 50,
                  "type": "INCREASE"
                }
                """;

        consumer.onWarehouseStockChanged(validJson);

        verify(eventLogServiceImpl, times(1))
                .save(
                        eq(EventType.WAREHOUSE_STOCK_CHANGED),
                        eq(SourceService.WAREHOUSE),
                        any(String.class),
                        eq(0),
                        eq(""));
    }

    @Test
    void should_throw_exception_when_message_is_invalid() {
        String invalidJson = "esto-no-es-json";

        assertThatThrownBy(() -> consumer.onWarehouseStockChanged(invalidJson))
                .isInstanceOf(EventDeserializationException.class)
                .hasMessageContaining("Error processing warehouse.stock.changed.v1");
    }

    @Test
    void should_process_replenishment_requested_event() {
        String validJson =
                """
                {
                  "productId": "abc-123",
                  "quantity": 50,
                  "type": "REPLENISHMENT"
                }
                """;

        consumer.onReplenishmentRequested(validJson);

        verify(eventLogServiceImpl, times(1))
                .save(
                        eq(EventType.REPLENISHMENT_REQUESTED),
                        eq(SourceService.WAREHOUSE),
                        any(String.class),
                        eq(0),
                        eq(""));
    }

    @Test
    void should_throw_exception_when_replenishment_message_is_invalid() {
        String invalidJson = "esto-no-es-json";

        assertThatThrownBy(() -> consumer.onReplenishmentRequested(invalidJson))
                .isInstanceOf(EventDeserializationException.class)
                .hasMessageContaining("Error processing replenishment.requested.v1");
    }

    @Test
    void should_throw_data_access_exception_when_warehouse_stock_changed_db_fails() {
        String validJson =
                """
                {
                  "productId": "abc-123",
                  "quantity": 50,
                  "type": "INCREASE"
                }
                """;

        doThrow(new DataAccessException("DB Error") {})
                .when(eventLogServiceImpl)
                .save(
                        eq(EventType.WAREHOUSE_STOCK_CHANGED),
                        eq(SourceService.WAREHOUSE),
                        any(String.class),
                        eq(0),
                        eq(""));

        assertThatThrownBy(() -> consumer.onWarehouseStockChanged(validJson))
                .isInstanceOf(DataAccessException.class)
                .hasMessage("DB Error");
    }

    @Test
    void should_throw_data_access_exception_when_replenishment_requested_db_fails() {
        String validJson =
                """
                {
                  "productId": "abc-123",
                  "quantity": 50,
                  "type": "REPLENISHMENT"
                }
                """;

        doThrow(new DataAccessException("DB Error") {})
                .when(eventLogServiceImpl)
                .save(
                        eq(EventType.REPLENISHMENT_REQUESTED),
                        eq(SourceService.WAREHOUSE),
                        any(String.class),
                        eq(0),
                        eq(""));

        assertThatThrownBy(() -> consumer.onReplenishmentRequested(validJson))
                .isInstanceOf(DataAccessException.class)
                .hasMessage("DB Error");
    }

    @Test
    void should_throw_runtime_exception_when_warehouse_stock_changed_unexpected_error() {
        String validJson =
                """
                {
                  "productId": "abc-123",
                  "quantity": 50,
                  "type": "INCREASE"
                }
                """;

        doThrow(new IllegalStateException("Unexpected Error"))
                .when(eventLogServiceImpl)
                .save(
                        eq(EventType.WAREHOUSE_STOCK_CHANGED),
                        eq(SourceService.WAREHOUSE),
                        any(String.class),
                        eq(0),
                        eq(""));

        assertThatThrownBy(() -> consumer.onWarehouseStockChanged(validJson))
                .isInstanceOf(EventProcessingException.class)
                .hasMessageContaining("Error processing warehouse.stock.changed.v1");
    }

    @Test
    void should_throw_runtime_exception_when_replenishment_requested_unexpected_error() {
        String validJson =
                """
                {
                  "productId": "abc-123",
                  "quantity": 50,
                  "type": "REPLENISHMENT"
                }
                """;

        doThrow(new IllegalStateException("Unexpected Error"))
                .when(eventLogServiceImpl)
                .save(
                        eq(EventType.REPLENISHMENT_REQUESTED),
                        eq(SourceService.WAREHOUSE),
                        any(String.class),
                        eq(0),
                        eq(""));

        assertThatThrownBy(() -> consumer.onReplenishmentRequested(validJson))
                .isInstanceOf(EventProcessingException.class)
                .hasMessageContaining("Error processing replenishment.requested.v1");
    }
}
