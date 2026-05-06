package org.example.reportsworskhopgft.eventlog.application;

import org.example.reportsworskhopgft.eventlog.domain.*;
import org.springframework.stereotype.Service;

@Service
public class EventLogServiceImpl {

    private final EventLogRepository eventLogRepository;

    public EventLogServiceImpl(EventLogRepository eventLogRepository) {
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