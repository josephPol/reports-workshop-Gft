package org.example.reportsworskhopgft.eventlog.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.reportsworskhopgft.eventlog.application.EventLogService;
import org.example.reportsworskhopgft.eventlog.application.TruckRegisteredEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransportEventConsumer {

    private final EventLogService eventLogService;

    //Traduce texto JSON a nuestro Record.
    private final ObjectMapper objectMapper;

    //Cola
    @RabbitListener(queues = "truck.registered.v1")
    public void onTruckRegistered(String message) {
        try {

            TruckRegisteredEvent event = objectMapper.readValue(message, TruckRegisteredEvent.class);
            eventLogService.logEvent(event);

        } catch (Exception e) {

            System.err.println("Error procesando mensaje de camión: " + e.getMessage());
        }
    }
}