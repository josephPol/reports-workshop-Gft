package org.example.reportsworskhopgft.eventlog.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.reportsworskhopgft.eventlog.application.impl.EventLogServiceImpl;
import org.example.reportsworskhopgft.eventlog.domain.EventType;
import org.example.reportsworskhopgft.eventlog.domain.SourceService;
import org.example.reportsworskhopgft.eventlog.infrastructure.messaging.truck.TruckPositionUpdateEvent;
import org.example.reportsworskhopgft.eventlog.infrastructure.messaging.truck.TruckRegisteredEvent;
import org.example.reportsworskhopgft.eventlog.infrastructure.messaging.truck.TruckStatusChangedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Slf4j
@Component
@RequiredArgsConstructor
public class TransportEventConsumer {

    private final EventLogServiceImpl eventLogServiceImpl;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "truck.registered.v1")
    public void onTruckRegistered(String message) {
        try {

            TruckRegisteredEvent event = objectMapper.readValue(message, TruckRegisteredEvent.class);

            eventLogServiceImpl.save(EventType.TRUCK_REGISTERED, SourceService.TRANSPORT, message, event.timestamp(), LocalDateTime.now().toString());
        } catch (Exception e) {
            log.error("Error processing truck message. Payload: {}", message, e);
            throw new RuntimeException("Error deserializing RabbitMQ event", e);
        }
    }

    @RabbitListener(queues = "truck.position.updated.v1")
    public void onTruckPositionUpdate(String message) {
        try {

            TruckPositionUpdateEvent event = objectMapper.readValue(message, TruckPositionUpdateEvent.class);

            eventLogServiceImpl.save(EventType.TRUCK_POSITION_UPDATED, SourceService.TRANSPORT, message, event.simulationDay(), event.timestamp());

        } catch (Exception e) {

            log.error("Error processing truck.position.update.v1. Payload: {}",message, e);
            throw new RuntimeException("Error processing truck position event", e);
        }
    }
    @RabbitListener(queues = "truck.status.changed.v1")
    public void onTruckStatusChanged(String message) {
        try {

            TruckStatusChangedEvent event = objectMapper.readValue(message, TruckStatusChangedEvent.class);


            eventLogServiceImpl.save(
                    EventType.TRUCK_STATUS_CHANGED,
                    SourceService.TRANSPORT,
                    objectMapper.writeValueAsString(event),
                    event.simulationDay(),
                    event.timestamp()
            );

        } catch (Exception e) {

            log.error("Error processing truck.status.changed.v1. Payload: {}", message, e);
            throw new RuntimeException("Error processing truck status event", e);
        }
    }
    @RabbitListener(queues = "delivery.created.v1")
    public void onDeliveryCreated(String message) {
        try {

            DeliveryCreatedEvent event = objectMapper.readValue(message, DeliveryCreatedEvent.class);


            eventLogServiceImpl.save(
                    EventType.DELIVERY_CREATED,
                    SourceService.TRANSPORT,
                    objectMapper.writeValueAsString(event),
                    event.simulationDay(),
                    event.timestamp()
            );

        } catch (Exception e) {

            log.error("Error processing delivery.created.v1. Payload: {}", message, e);
            throw new RuntimeException("Error processing delivery created event", e);
        }
    }
    @RabbitListener(queues = "delivery.completed.v1")
    public void onDeliveryCompleted(String message) {
        try {
            DeliveryCompletedEvent event = objectMapper.readValue(message, DeliveryCompletedEvent.class);

            eventLogServiceImpl.save(
                    EventType.DELIVERY_COMPLETED,
                    SourceService.TRANSPORT,
                    objectMapper.writeValueAsString(event),
                    event.simulationDay(),
                    event.timestamp()
            );


            TruckStatusChangedEvent statusEvent = new TruckStatusChangedEvent(
                    event.truckId(),
                    "AVAILABLE",
                    event.simulationDay(),
                    event.timestamp()
            );

            eventLogServiceImpl.save(
                    EventType.TRUCK_STATUS_CHANGED,
                    SourceService.TRANSPORT,
                    objectMapper.writeValueAsString(statusEvent),
                    event.simulationDay(),
                    event.timestamp()
            );

        } catch (Exception e) {
            log.error("Error processing delivery.completed.v1. Payload: {}", message, e);
            throw new RuntimeException("Error processing delivery completed event", e);
        }
    }
}
