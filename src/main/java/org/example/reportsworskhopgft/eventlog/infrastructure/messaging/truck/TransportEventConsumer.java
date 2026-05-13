package org.example.reportsworskhopgft.eventlog.infrastructure.messaging.truck;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
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
public class TransportEventConsumer {

    private final EventLogServiceImpl eventLogServiceImpl;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitMQConfig.TRUCK_REGISTERED_QUEUE_NAME)
    public void onTruckRegistered(TruckRegisteredEvent event) {
        try {
            log.info("Evento de producción recibido: {}", event);

            String jsonPayload = objectMapper.writeValueAsString(event);

            eventLogServiceImpl.save(
                    EventType.TRUCK_REGISTERED,
                    SourceService.TRANSPORT,
                    jsonPayload,
                    event.timestamp(),
                    LocalDateTime.now().toString());

        } catch (Exception e) {
            log.error("Error processing truck message. Payload: {}", event, e);
            throw new RuntimeException("Error deserializing RabbitMQ event", e);
        }
    }

    @RabbitListener(queues = RabbitMQConfig.TRUCK_POSITION_UPDATE_QUEUE_NAME)
    public void onTruckPositionUpdate(TruckPositionUpdateEvent event) {
        try {
            log.info("Evento de producción recibido: {}", event);

            String jsonPayload = objectMapper.writeValueAsString(event);

            eventLogServiceImpl.save(
                    EventType.TRUCK_POSITION_UPDATED,
                    SourceService.TRANSPORT,
                    jsonPayload,
                    event.simulationDay(),
                    event.timestamp());

        } catch (Exception e) {

            log.error("Error processing truck.position.update.v1. Payload: {}", event, e);
            throw new RuntimeException("Error processing truck position event", e);
        }
    }

    @RabbitListener(queues = RabbitMQConfig.TRUCK_STATUS_CHANGED_QUEUE_NAME)
    public void onTruckStatusChanged(TruckStatusChangedEvent event) {
        try {
            log.info("Evento de producción recibido: {}", event);

            String jsonPayload = objectMapper.writeValueAsString(event);

            eventLogServiceImpl.save(
                    EventType.TRUCK_STATUS_CHANGED,
                    SourceService.TRANSPORT,
                    jsonPayload,
                    event.simulationDay(),
                    event.timestamp());

        } catch (Exception e) {

            log.error("Error processing truck.status.changed.v1. Payload: {}", event, e);
            throw new RuntimeException("Error processing truck status event", e);
        }
    }

    //    @RabbitListener(queues = "delivery.created.v1")
    //    public void onDeliveryCreated(String message) {
    //        try {
    //
    //            DeliveryCreatedEvent event = objectMapper.readValue(message,
    // DeliveryCreatedEvent.class);
    //
    //
    //            eventLogServiceImpl.save(
    //                    EventType.DELIVERY_CREATED,
    //                    SourceService.TRANSPORT,
    //                    objectMapper.writeValueAsString(event),
    //                    event.simulationDay(),
    //                    event.timestamp()
    //            );
    //
    //        } catch (Exception e) {
    //
    //            log.error("Error processing delivery.created.v1. Payload: {}", message, e);
    //            throw new RuntimeException("Error processing delivery created event", e);
    //        }
    //    }
    @RabbitListener(queues = RabbitMQConfig.DELIVERY_COMPLETED_QUEUE_NAME)
    public void onDeliveryCompleted(DeliveryCompletedEvent event) {
        try {
            log.info("Evento de producción recibido: {}", event);

            String jsonPayload = objectMapper.writeValueAsString(event);

            eventLogServiceImpl.save(
                    EventType.DELIVERY_COMPLETED,
                    SourceService.TRANSPORT,
                    jsonPayload,
                    event.simulationDay(),
                    event.timestamp());

            // Also save truck status changed to available
            eventLogServiceImpl.save(
                    EventType.TRUCK_STATUS_CHANGED,
                    SourceService.TRANSPORT,
                    jsonPayload,
                    event.simulationDay(),
                    event.timestamp());

        } catch (Exception e) {
            log.error("Error processing delivery.completed.v1. Payload: {}", event, e);
            throw new RuntimeException("Error processing delivery completed event", e);
        }
    }
}
