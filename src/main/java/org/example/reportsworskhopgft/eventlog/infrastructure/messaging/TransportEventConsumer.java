package org.example.reportsworskhopgft.eventlog.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.reportsworskhopgft.eventlog.application.EventLogService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransportEventConsumer {


    private final EventLogService eventLogService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "truck.registered.v1")
    public void onTruckRegistered(String message) {
        try {

            TruckRegisteredEvent event = objectMapper.readValue(message, TruckRegisteredEvent.class);


            eventLogService.save(
                    "TRUCK_REGISTERED",
                    "TRANSPORT",
                    message, // Guardamos el texto JSON original entero en la base de datos
                    event.timestamp(),
                    LocalDateTime.now().toString()
            );
        } catch (Exception e) {
            log.error("Error procesando mensaje de camión. Payload: {}", message, e);
            throw new RuntimeException("Error deserializando el evento de RabbitMQ", e);
        }
    }
}