package org.example.reportsworskhopgft.eventlog.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.reportsworskhopgft.eventlog.domain.EventType;
import org.example.reportsworskhopgft.eventlog.domain.SourceService;
import org.example.reportsworskhopgft.eventlog.application.EventLogServiceImpl;
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
            TimeAdvancedMessage event = objectMapper.readValue(message, TimeAdvancedMessage.class);
            eventLogServiceImpl.save(
                    EventType.TIME_ADVANCED,
                    SourceService.TIME,
                    message,
                    event.simulationDay(),
                    event.occurredAt()
            );
        }catch (Exception e) {
        log.error("Error processing time.advanced.v1. Payload: {}", message, e);
        throw new RuntimeException("Error processing time.advanced.v1", e);
    }
    }

}