package org.example.reportsworskhopgft.eventlog.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.reportsworskhopgft.eventlog.application.EventLogServiceImpl;
import org.example.reportsworskhopgft.eventlog.domain.EventType;
import org.example.reportsworskhopgft.eventlog.domain.SourceService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WarehouseEventConsumer {

    private final EventLogServiceImpl eventLogServiceImpl;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "warehouse-stock-changed.v1")
    public void onWarehouseStockChanged(String message) {
        try {
            WarehouseStockChangedMessage event = objectMapper.readValue(message, WarehouseStockChangedMessage.class);
            eventLogServiceImpl.save(
                    EventType.WAREHOUSE_STOCK_CHANGED,
                    SourceService.WAREHOUSE,
                    event.toPayload(),
                    0,
                    ""
            );
        } catch (Exception e) {
            log.error("Error processing warehouse.stock.changed.v1. Payload: {}", message, e);
            throw new RuntimeException("Error processing warehouse.stock.changed.v1", e);
        }
    }
}