package org.example.reportsworskhopgft.eventlog.application.impl;

import org.example.reportsworskhopgft.eventlog.domain.EventLog;
import org.example.reportsworskhopgft.eventlog.infrastructure.persistence.EventLogJPA;
import org.example.reportsworskhopgft.eventlog.domain.EventLogId;
import org.example.reportsworskhopgft.eventlog.domain.EventType;
import org.example.reportsworskhopgft.eventlog.domain.SourceService;
import org.example.reportsworskhopgft.eventlog.infrastructure.EventLogRepositoryJPA;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventLogServiceImpl {

    private final EventLogRepositoryJPA eventLogRepositoryJPA;

    public EventLogServiceImpl(EventLogRepositoryJPA eventLogRepositoryJPA) {
        this.eventLogRepositoryJPA = eventLogRepositoryJPA;
    }


    public void save(EventType eventType, SourceService sourceService, String payload, Integer simulationDay, String occurredAt) {
        EventLogJPA eventLogJPA = new EventLogJPA(
                EventLogId.generate(),
                eventType,
                sourceService,
                payload,
                simulationDay,
                occurredAt);
        eventLogRepositoryJPA.save(eventLogJPA);
    }

    public List<EventLogJPA> findAll() {
        return eventLogRepositoryJPA.findAll();
    }
}