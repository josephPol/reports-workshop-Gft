package org.example.reportsworskhopgft.eventlog.infrastructure.messaging.warehouse;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.reportsworskhopgft.eventlog.application.impl.EventLogServiceImpl;
import org.example.reportsworskhopgft.eventlog.domain.EventType;
import org.example.reportsworskhopgft.eventlog.domain.SourceService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WarehouseEventConsumer {

    private final EventLogServiceImpl eventLogService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "warehouse.stock.changed.v1")
    public void onWarehouseStockChanged(String message) {
        try {
            WarehouseStockChangedMessage event =
                    objectMapper.readValue(message, WarehouseStockChangedMessage.class);
            eventLogService.save(
                    EventType.WAREHOUSE_STOCK_CHANGED, SourceService.WAREHOUSE, message, 0, "");
        } catch (Exception e) {
            log.error("Error processing warehouse.stock.changed.v1. Payload: {}", message, e);
            throw new RuntimeException("Error processing warehouse.stock.changed.v1", e);
        }
    }

    @RabbitListener(queues = "replenishment.requested.v1")
    public void onReplenishmentRequested(String message) {
        try {
            ReplenishmentRequestedMessage event =
                    objectMapper.readValue(message, ReplenishmentRequestedMessage.class);
            eventLogService.save(
                    EventType.REPLENISHMENT_REQUESTED,
                    SourceService.WAREHOUSE,
                    event.toPayload(),
                    0,
                    "");
        } catch (Exception e) {
            log.error("Error processing replenishment.requested.v1. Payload: {}", message, e);
            throw new RuntimeException("Error processing replenishment.requested.v1", e);
        }
    }
}
