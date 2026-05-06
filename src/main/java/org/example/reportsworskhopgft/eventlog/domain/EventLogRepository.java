package org.example.reportsworskhopgft.eventlog.domain;

import org.springframework.stereotype.Repository;

@Repository
public interface EventLogRepository {
    void save(EventLog eventLog);
}