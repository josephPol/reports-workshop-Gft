package org.example.reportsworskhopgft.eventlog.application.impl;

import org.example.reportsworskhopgft.eventlog.infrastructure.persistence.EventLogJPA;
import org.example.reportsworskhopgft.eventlog.domain.EventType;
import org.example.reportsworskhopgft.eventlog.domain.SourceService;
import org.example.reportsworskhopgft.eventlog.infrastructure.EventLogRepositoryJPA;
import org.example.reportsworskhopgft.eventlog.infrastructure.persistence.EventLogIdJPA;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EventLogServiceImpl {

    private final EventLogRepositoryJPA eventLogRepositoryJPA;

    public EventLogServiceImpl(EventLogRepositoryJPA eventLogRepositoryJPA) {
        this.eventLogRepositoryJPA = eventLogRepositoryJPA;
    }


    public void save(EventType eventType, SourceService sourceService, String payload, Integer simulationDay, String occurredAt) {
        EventLogJPA eventLogJPA = new EventLogJPA(
                new EventLogIdJPA(UUID.randomUUID().toString()),
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
