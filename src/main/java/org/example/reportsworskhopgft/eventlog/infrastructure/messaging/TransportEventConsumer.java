package org.example.reportsworskhopgft.eventlog.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j; // <-- Importante
import org.example.reportsworskhopgft.eventlog.application.EventLogService;
import org.example.reportsworskhopgft.eventlog.application.TruckRegisteredEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j // 1. Añadimos el Logger de Lombok
public class TransportEventConsumer {

    private final EventLogService eventLogService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "truck.registered.v1")
    public void onTruckRegistered(String message) {
        try {
            TruckRegisteredEvent event = objectMapper.readValue(message, TruckRegisteredEvent.class);
            eventLogService.logEvent(event);
        } catch (Exception e) {
            // 2. Usamos log.error y le pasamos la excepción 'e' completa para ver toda la traza
            log.error("Error procesando mensaje de camión. Payload: {}", message, e);

            // 3. Relanzamos la excepción. En RabbitMQ, si tragas el error,
            // el sistema piensa que el mensaje se procesó bien. Hay que avisar del fallo.
            throw new RuntimeException("Error deserializando el evento de RabbitMQ", e);
        }
    }
}
