package org.example.reportsworskhopgft.eventlog.application;

import org.example.reportsworskhopgft.eventlog.domain.EventLog;
import org.example.reportsworskhopgft.eventlog.domain.EventLogId;

import java.util.List;

public interface EventLogService {
    List<EventLog> findAllEventsLogs();
    EventLog findEventLogById(EventLogId id);
    void save(EventLog eventLog);
}
