package org.example.reportsworskhopgft.eventlog.infrastructure.messaging.warehouse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.reportsworskhopgft.eventlog.application.impl.EventLogServiceImpl;
import org.example.reportsworskhopgft.eventlog.domain.EventType;
import org.example.reportsworskhopgft.eventlog.domain.SourceService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WarehouseEventConsumer {

    private final EventLogServiceImpl eventLogService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "warehouse.stock.changed.v1")
    public void onWarehouseStockChanged(String event) {
        try {
            WarehouseStockChangedMessage object =
                    objectMapper.readValue(event, WarehouseStockChangedMessage.class);
            eventLogService.save(
                    EventType.WAREHOUSE_STOCK_CHANGED, SourceService.WAREHOUSE, event, 0, "");
        } catch (JsonProcessingException e) {
            log.error("Error serializing event to JSON: {}", event, e);
        } catch (DataAccessException e) {
            log.error("Database error while saving log: {}", event, e);
            throw e;
        } catch (RuntimeException e) {
            log.error("Unexpected error processing production order: {}", event, e);
            throw e;
        }
    }

    @RabbitListener(queues = "replenishment.requested.v1")
    public void onReplenishmentRequested(String event) {
        try {
            ReplenishmentRequestedMessage object =
                    objectMapper.readValue(event, ReplenishmentRequestedMessage.class);
            eventLogService.save(
                    EventType.REPLENISHMENT_REQUESTED,
                    SourceService.WAREHOUSE,
                    object.toPayload(),
                    0,
                    "");
        } catch (JsonProcessingException e) {
            log.error("Error serializing event to JSON: {}", event, e);
        } catch (DataAccessException e) {
            log.error("Database error while saving log: {}", event, e);
            throw e;
        } catch (RuntimeException e) {
            log.error("Unexpected error processing production order: {}", event, e);
            throw e;
        }
    }
}
