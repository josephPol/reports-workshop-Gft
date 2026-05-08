package org.example.reportsworskhopgft.eventlog.infrastructure.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.reportsworskhopgft.eventlog.domain.EventType;
import org.example.reportsworskhopgft.eventlog.domain.SourceService;
import org.example.reportsworskhopgft.eventlog.application.impl.EventLogServiceImpl;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TimeEventConsumer {

    private final EventLogServiceImpl eventLogServiceImpl;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "time.advanced.v1")
    public void onTimeAdvanced(String message) {
        try {
            String jsonToProcess = message;
            if (message.startsWith("\"")) {
                jsonToProcess = objectMapper.readValue(message, String.class);
            }

            TimeAdvancedMessage event = objectMapper.readValue(jsonToProcess, TimeAdvancedMessage.class);

            eventLogServiceImpl.save(
                    EventType.TIME_ADVANCED,
                    SourceService.TIME,
                    jsonToProcess,
                    event.simulationDay(),
                    event.occurredAt()
            );
        } catch (JsonProcessingException e) {
            log.error("Error al deserializar el mensaje: {}", message, e);
            throw new RuntimeException("Error processing time.advanced.v1", e);
        }
    }

}