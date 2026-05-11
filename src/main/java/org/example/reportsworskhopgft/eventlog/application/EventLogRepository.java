package org.example.reportsworskhopgft.eventlog.application;

import org.example.reportsworskhopgft.eventlog.domain.EventLog;
import org.example.reportsworskhopgft.eventlog.domain.EventLogId;
import org.example.reportsworskhopgft.eventlog.infrastructure.persistence.EventLogJPA;

import java.util.List;

public interface EventLogRepository {
    List<EventLog> findAllEventsLogs();
    EventLog findEventLogById(EventLogId id);
    void save(EventLog eventLog);
}
