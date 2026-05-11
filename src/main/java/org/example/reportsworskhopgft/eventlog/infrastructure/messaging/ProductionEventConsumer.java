package org.example.reportsworskhopgft.eventlog.infrastructure.messaging;

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
public class ProductionEventConsumer {

    private final EventLogServiceImpl eventLogService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "production.order.created.v1")
    public void onProductionOrderCreated(String message) {
        try {
            ProductionOrderCreatedMessage event = objectMapper.readValue(message, ProductionOrderCreatedMessage.class);

            eventLogService.save(
                    EventType.PRODUCTION_ORDER_CREATED,
                    SourceService.FACTORY,
                    message,
                    event.simulationDay(),
                    event.occurredAt()
            );
        } catch (Exception e) {
            log.error("Error processing production.order.created.v1. Payload: {}", message, e);
            throw new RuntimeException("Error processing production.order.created.v1", e);
        }
    }
}