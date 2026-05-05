package org.example.reportsworskhopgft.eventlog.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.reportsworskhopgft.eventlog.domain.EventLog;
import org.example.reportsworskhopgft.eventlog.domain.EventLogId;
import org.example.reportsworskhopgft.eventlog.domain.EventType;
import org.example.reportsworskhopgft.eventlog.domain.SourceService;
import org.example.reportsworskhopgft.eventlog.infrastructure.persistence.EventLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventLogService {

    private final EventLogRepository repository;
    private final ObjectMapper objectMapper;

    public void logEvent(TruckRegisteredEvent event) {
        try {

            String payloadJson = objectMapper.writeValueAsString(event);


            EventLog newLog = new EventLog();
            newLog.setId(new EventLogId(UUID.randomUUID()));
            newLog.setEventType(EventType.TRUCK_REGISTERED);
            newLog.setSourceService(SourceService.TRANSPORT);
            newLog.setPayload(payloadJson);
            newLog.setSimulationDay(event.timestamp());
            newLog.setOcurredAt(LocalDateTime.now().toString());


            repository.save(newLog);

        } catch (Exception e) {
            System.err.println("Error guardando el historial: " + e.getMessage());
        }
    }
}
