package org.example.reportsworskhopgft.eventlog.application;

import org.example.reportsworskhopgft.eventlog.infrastructure.persistence.EventLogJPA;

import java.util.List;

public interface EventLogRepository {
    List<EventLogJPA> findAllEventsLogs();
    EventLogJPA findEventLogById(Integer id);
}
