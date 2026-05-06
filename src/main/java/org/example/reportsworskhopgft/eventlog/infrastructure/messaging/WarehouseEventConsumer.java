package org.example.reportsworskhopgft.eventlog.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.reportsworskhopgft.eventlog.application.EventLogService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WarehouseEventConsumer {

    private final EventLogService eventLogService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "${rabbitmq.queues.warehouse-stock-changed}")
    public void onWarehouseStockChanged(String message) {
        try {
            WarehouseStockChangedMessage event = objectMapper.readValue(message, WarehouseStockChangedMessage.class);
            eventLogService.save(
                    "warehouse.stock.changed.v1",
                    "warehouse",
                    event.toPayload(),
                    0,
                    ""
            );
        } catch (Exception e) {
            log.error("Error procesando warehouse.stock.changed.v1. Payload: {}", message, e);
            throw new RuntimeException("Error procesando warehouse.stock.changed.v1", e);
        }
    }
}