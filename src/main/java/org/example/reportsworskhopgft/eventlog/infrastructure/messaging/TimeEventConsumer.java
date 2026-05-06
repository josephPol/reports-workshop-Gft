package org.example.reportsworskhopgft.eventlog.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.reportsworskhopgft.eventlog.domain.EventType;
import org.example.reportsworskhopgft.eventlog.domain.SourceService;
import org.example.reportsworskhopgft.eventlog.application.EventLogService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TimeEventConsumer {

    private final EventLogService eventLogService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "${rabbitmq.queues.time-advanced}")
    public void onTimeAdvanced(String message) {
        try {
            TimeAdvancedMessage event = objectMapper.readValue(message, TimeAdvancedMessage.class);
            eventLogService.save(
                    EventType.TIME_ADVANCED,
                    SourceService.TIME,
                    event.toPayload(),
                    event.simulationDay(),
                    event.occurredAt()
            );
        } catch (Exception e) {
            log.error("Error procesando time.advanced.v1. Payload: {}", message, e);
            throw new RuntimeException("Error procesando time.advanced.v1", e);
        }
    }

}