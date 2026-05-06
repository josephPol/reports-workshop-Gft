package org.example.reportsworskhopgft.eventlog.domain;

import org.springframework.stereotype.Repository;

@Repository
public interface EventLogService {
    void save(EventLog eventLog);
}