package org.example.reportsworkshopgft.eventlog.infrastructure.messaging.warehouse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.reportsworkshopgft.eventlog.application.impl.EventLogServiceImpl;
import org.example.reportsworkshopgft.eventlog.domain.EventType;
import org.example.reportsworkshopgft.eventlog.domain.SourceService;
import org.example.reportsworkshopgft.eventlog.infrastructure.messaging.exception.EventDeserializationException;
import org.example.reportsworkshopgft.eventlog.infrastructure.messaging.exception.EventProcessingException;
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
        final String eventName = "warehouse.stock.changed.v1";
        try {
            log.info("Production event received: {}", event);
            objectMapper.readValue(event, WarehouseStockChangedMessage.class);
            eventLogService.save(
                    EventType.WAREHOUSE_STOCK_CHANGED, SourceService.WAREHOUSE, event, 0, "");
        } catch (JsonProcessingException e) {
            log.error("Error deserializing JSON event: {}", event, e);
            throw new EventDeserializationException(
                    eventName, "Error processing warehouse.stock.changed.v1", e);
        } catch (DataAccessException e) {
            log.error("Database error while saving log: {}", event, e);
            throw e;
        } catch (RuntimeException e) {
            log.error("Unexpected error processing production order: {}", event, e);
            throw new EventProcessingException(
                    eventName, "Error processing warehouse.stock.changed.v1", e);
        }
    }

    @RabbitListener(queues = "replenishment.requested.v1")
    public void onReplenishmentRequested(String event) {
        final String eventName = "replenishment.requested.v1";
        try {
            ReplenishmentRequestedMessage event11 =
                    objectMapper.readValue(event, ReplenishmentRequestedMessage.class);
            eventLogService.save(
                    EventType.REPLENISHMENT_REQUESTED,
                    SourceService.WAREHOUSE,
                    event11.toPayload(),
                    0,
                    "");
        } catch (JsonProcessingException e) {
            log.error("Error deserializing JSON event: {}", event, e);
            throw new EventDeserializationException(
                    eventName, "Error processing replenishment.requested.v1", e);
        } catch (DataAccessException e) {
            log.error("Database error while saving log: {}", event, e);
            throw e;
        } catch (RuntimeException e) {
            log.error("Unexpected error processing production order: {}", event, e);
            throw new EventProcessingException(
                    eventName, "Error processing replenishment.requested.v1", e);
        }
    }

    @RabbitListener(queues = "warehouse.order.blocked.v1")
    public void onWarehouseOrderBlocked(String event) {
        final String eventName = "warehouse.order.blocked.v1";
        try {
            log.info("Warehouse blocked order event received: {}", event);
            objectMapper.readValue(event, WarehouseOrderBlockedEvent.class);

            eventLogService.save(
                    EventType.WAREHOUSE_ORDER_BLOCKED, SourceService.WAREHOUSE, event, 0, "");
        } catch (JsonProcessingException e) {
            log.error("Error deserializing JSON event: {}", event, e);
            throw new EventDeserializationException(
                    eventName, "Error processing warehouse.order.blocked.v1", e);
        } catch (DataAccessException e) {
            log.error("Database error while saving log: {}", event, e);
            throw e;
        } catch (RuntimeException e) {
            log.error("Unexpected error processing blocked order: {}", event, e);
            throw new EventProcessingException(
                    eventName, "Error processing warehouse.order.blocked.v1", e);
        }
    }
}
