package org.example.reportsworskhopgft.eventlog.application;

import org.example.reportsworskhopgft.eventlog.domain.EventLog;
import org.example.reportsworskhopgft.eventlog.domain.EventLogRepository;
import org.example.reportsworskhopgft.eventlog.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final EventLogRepository eventLogRepository;

    public EventLogService(EventLogRepository eventLogRepository) {
        this.eventLogRepository = eventLogRepository;
    }


    public void save(EventType eventType, SourceService sourceService,
                     String payload, Integer simulationDay, String occurredAt) {
        EventLog eventLog = new EventLog(
                EventLogId.generate(),
                eventType,
                sourceService,
                payload,
                simulationDay,
                occurredAt
        );
        eventLogRepository.save(eventLog);
    }
}