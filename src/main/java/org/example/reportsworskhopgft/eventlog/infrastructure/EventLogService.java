package org.example.reportsworskhopgft.eventlog.infrastructure;

import org.example.reportsworskhopgft.eventlog.domain.EventLog;
import org.example.reportsworskhopgft.eventlog.domain.EventLogRepository;
import org.springframework.stereotype.Service;

@Service
public class EventLogService {

    private final EventLogRepository repository;

    public EventLogService(EventLogRepository repository) {
        this.repository = repository;
    }

    public void save(String eventType, String sourceService,
                     String payload, int simulationDay, String occurredAt) {
        EventLog eventLog = EventLog.create(eventType, sourceService, payload, simulationDay, occurredAt);
        repository.save(eventLog);
    }
}