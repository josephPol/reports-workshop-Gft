package org.example.reportsworskhopgft.eventlog.application;

import org.example.reportsworskhopgft.eventlog.infrastructure.persistence.EventLogJPA;

public interface EventLogService {
    void save(EventLogJPA eventLogJPA);
}