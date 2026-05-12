package org.example.reportsworskhopgft.eventlog.infrastructure.messaging.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.reportsworskhopgft.eventlog.application.impl.EventLogServiceImpl;
import org.example.reportsworskhopgft.eventlog.domain.EventType;
import org.example.reportsworskhopgft.eventlog.domain.SourceService;
import org.example.reportsworskhopgft.rabbitmq.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductionEventConsumer {

    private final EventLogServiceImpl eventLogService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitMQConfig.PRODUCTION_ORDER_CREATED_QUEUE_NAME)
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

    @RabbitListener(queues = RabbitMQConfig.PRODUCTION_ORDER_STARTED_QUEUE_NAME)
    public void onProductionOrderStarted(String message) {
        try {
            ProductionOrderStartedMessage event = objectMapper.readValue(message, ProductionOrderStartedMessage.class);

            eventLogService.save(
                    EventType.PRODUCTION_ORDER_STARTED,
                    SourceService.FACTORY,
                    message,
                    event.simulationDay(),
                    event.occurredAt()
            );
        } catch (Exception e) {
            log.error("Error processing production.order.started.v1. Payload: {}", message, e);
            throw new RuntimeException("Error processing production.order.started.v1", e);
        }
    }

    @RabbitListener(queues = RabbitMQConfig.PRODUCTION_ORDER_COMPLETED_QUEUE_NAME)
    public void onProductionOrderCompleted(String message) {
        try {
            ProductionOrderCompletedMessage event = objectMapper.readValue(message, ProductionOrderCompletedMessage.class);

            eventLogService.save(
                    EventType.PRODUCTION_ORDER_COMPLETED,
                    SourceService.FACTORY,
                    message,
                    event.simulationDay(),
                    event.occurredAt()
            );
        } catch (Exception e) {
            log.error("Error processing production.order.completed.v1. Payload: {}", message, e);
            throw new RuntimeException("Error processing production.order.completed.v1", e);
        }
    }

    @RabbitListener(queues = RabbitMQConfig.PRODUCTION_ORDER_BLOCKED_QUEUE_NAME)
    public void onProductionOrderBlocked(String message) {
        try {
            ProductionOrderBlockedMessage event = objectMapper.readValue(message, ProductionOrderBlockedMessage.class);

            eventLogService.save(
                    EventType.PRODUCTION_ORDER_BLOCKED,
                    SourceService.FACTORY,
                    message,
                    event.simulationDay(),
                    event.occurredAt()
            );
        } catch (Exception e) {
            log.error("Error processing production.order.completed.v1. Payload: {}", message, e);
            throw new RuntimeException("Error processing production.order.completed.v1", e);
        }
    }

    @RabbitListener(queues = RabbitMQConfig.RECIPE_REGISTERED_QUEUE_NAME)
    public void onRecipeRegistered(String message) {
        try {
            ProductionRecipeRegisteredMessage event = objectMapper.readValue(message, ProductionRecipeRegisteredMessage.class);

            eventLogService.save(
                    EventType.PRODUCTION_RECIPE_REGISTERED,
                    SourceService.FACTORY,
                    message,
                    event.simulationDay(),
                    event.occurredAt()
            );
        } catch (Exception e) {
            log.error("Error processing production.order.completed.v1. Payload: {}", message, e);
            throw new RuntimeException("Error processing production.order.completed.v1", e);
        }
    }

    @RabbitListener(queues = RabbitMQConfig.FACTORY_REGISTERED_QUEUE_NAME)
    public void onFactoryRegistered(String message) {
        try {
            ProductionFactoryRegisteredMessage event = objectMapper.readValue(message, ProductionFactoryRegisteredMessage.class);

            eventLogService.save(
                    EventType.PRODUCTION_FACTORY_REGISTERED,
                    SourceService.FACTORY,
                    message,
                    event.simulationDay(),
                    event.occurredAt()
            );
        } catch (Exception e) {
            log.error("Error processing production.order.completed.v1. Payload: {}", message, e);
            throw new RuntimeException("Error processing production.order.completed.v1", e);
        }
    }
}