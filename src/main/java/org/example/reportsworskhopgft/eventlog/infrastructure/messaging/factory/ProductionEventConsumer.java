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
    public void onProductionOrderCreated(ProductionOrderCreatedMessage event) {
        try {
            log.info("Evento de producción recibido: {}", event);

            String jsonPayload = objectMapper.writeValueAsString(event);

            eventLogService.save(
                    EventType.PRODUCTION_ORDER_CREATED,
                    SourceService.FACTORY,
                    jsonPayload,
                    event.simulationDay(),
                    event.occurredAt()
            );
        } catch (Exception e) {
            log.error("Error processing production.order.created.v1: {}", event, e);
            throw new RuntimeException("Error processing production.order.created.v1", e);
        }
    }

    @RabbitListener(queues = RabbitMQConfig.PRODUCTION_ORDER_STARTED_QUEUE_NAME)
    public void onProductionOrderStarted(ProductionOrderStartedMessage event) {
        try {
            log.info("Evento de producción recibido: {}", event);

            String jsonPayload = objectMapper.writeValueAsString(event);

            eventLogService.save(
                    EventType.PRODUCTION_ORDER_STARTED,
                    SourceService.FACTORY,
                    jsonPayload,
                    event.simulationDay(),
                    event.occurredAt()
            );
        } catch (Exception e) {
            log.error("Error processing production.order.started.v1. Payload: {}", event, e);
            throw new RuntimeException("Error processing production.order.started.v1", e);
        }
    }

    @RabbitListener(queues = RabbitMQConfig.PRODUCTION_ORDER_COMPLETED_QUEUE_NAME)
    public void onProductionOrderCompleted(ProductionOrderCompletedMessage event) {
        try {
            log.info("Evento de producción recibido: {}", event);

            String jsonPayload = objectMapper.writeValueAsString(event);

            eventLogService.save(
                    EventType.PRODUCTION_ORDER_COMPLETED,
                    SourceService.FACTORY,
                    jsonPayload,
                    event.simulationDay(),
                    event.occurredAt()
            );
        } catch (Exception e) {
            log.error("Error processing production.order.completed.v1. Payload: {}", event, e);
            throw new RuntimeException("Error processing production.order.completed.v1", e);
        }
    }

    @RabbitListener(queues = RabbitMQConfig.PRODUCTION_ORDER_BLOCKED_QUEUE_NAME)
    public void onProductionOrderBlocked(ProductionOrderBlockedMessage event) {
        try {
            log.info("Evento de producción recibido: {}", event);

            String jsonPayload = objectMapper.writeValueAsString(event);

            eventLogService.save(
                    EventType.PRODUCTION_ORDER_BLOCKED,
                    SourceService.FACTORY,
                    jsonPayload,
                    event.simulationDay(),
                    event.occurredAt()
            );
        } catch (Exception e) {
            log.error("Error processing production.order.completed.v1. Payload: {}", event, e);
            throw new RuntimeException("Error processing production.order.completed.v1", e);
        }
    }

    @RabbitListener(queues = RabbitMQConfig.RECIPE_REGISTERED_QUEUE_NAME)
    public void onRecipeRegistered(ProductionRecipeRegisteredMessage event) {
        try {
            log.info("Evento de producción recibido: {}", event);

            String jsonPayload = objectMapper.writeValueAsString(event);

            eventLogService.save(
                    EventType.PRODUCTION_RECIPE_REGISTERED,
                    SourceService.FACTORY,
                    jsonPayload,
                    event.simulationDay(),
                    event.occurredAt()
            );
        } catch (Exception e) {
            log.error("Error processing production.order.completed.v1. Payload: {}", event, e);
            throw new RuntimeException("Error processing production.order.completed.v1", e);
        }
    }

    @RabbitListener(queues = RabbitMQConfig.FACTORY_REGISTERED_QUEUE_NAME)
    public void onFactoryRegistered(ProductionFactoryRegisteredMessage event) {
        try {
            log.info("Evento de producción recibido: {}", event);

            String jsonPayload = objectMapper.writeValueAsString(event);

            eventLogService.save(
                    EventType.PRODUCTION_FACTORY_REGISTERED,
                    SourceService.FACTORY,
                    jsonPayload,
                    event.simulationDay(),
                    event.occurredAt()
            );
        } catch (Exception e) {
            log.error("Error processing production.order.completed.v1. Payload: {}", event, e);
            throw new RuntimeException("Error processing production.order.completed.v1", e);
        }
    }
}