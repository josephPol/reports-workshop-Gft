package org.example.reportsworskhopgft.eventlog.infrastructure.messaging.time;

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
public class TimeEventConsumer {

    private final EventLogServiceImpl eventLogServiceImpl;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitMQConfig.TIME_ADVANCED_QUEUE_NAME)
    public void onTimeAdvanced(TimeAdvancedMessage event) {
        try {
            log.info("Evento de producción recibido: {}", event);

            String jsonPayload = objectMapper.writeValueAsString(event);

            eventLogServiceImpl.save(
                    EventType.TIME_ADVANCED,
                    SourceService.TIME,
                    jsonPayload,
                    event.simulationDay(),
                    event.occurredAt()
            );
        } catch (Exception e) {
            log.error("Error processing time.advanced.v1: {}", event, e);
            throw new RuntimeException("Error processing time.advanced.v1", e);
        }
    }
}