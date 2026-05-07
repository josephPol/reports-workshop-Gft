package org.example.reportsworskhopgft.eventlog.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.reportsworskhopgft.eventlog.application.EventLogServiceImpl;
import org.example.reportsworskhopgft.eventlog.domain.EventType;
import org.example.reportsworskhopgft.eventlog.domain.SourceService;
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

            System.err.println("Error processing truck.position.update.v1. Payload: " + message);
            throw new RuntimeException("Error processing truck position event", e);
        }
    }
}