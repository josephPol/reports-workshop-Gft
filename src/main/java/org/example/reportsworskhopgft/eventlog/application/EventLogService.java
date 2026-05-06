package org.example.reportsworskhopgft.eventlog.application;

import org.example.reportsworskhopgft.eventlog.domain.*;
import org.springframework.stereotype.Service;

@Service
public class EventLogService {

    private final EventLogRepository repository;

    public EventLogService(EventLogRepository repository) {
        this.repository = repository;
    }

    public void save(EventType eventType, SourceService sourceService,
                     String payload, int simulationDay, String occurredAt) {
        EventLog eventLog = new EventLog(
                EventLogId.generate(),
                eventType,
                sourceService,
                payload,
                simulationDay,
                occurredAt
        );
        repository.save(eventLog);
    }
}