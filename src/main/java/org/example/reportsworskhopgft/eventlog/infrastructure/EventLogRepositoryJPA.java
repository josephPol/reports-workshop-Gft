package org.example.reportsworskhopgft.eventlog.infrastructure;

import org.example.reportsworskhopgft.eventlog.infrastructure.persistence.EventLogIdJPA;
import org.example.reportsworskhopgft.eventlog.infrastructure.persistence.EventLogJPA;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventLogRepositoryJPA extends JpaRepository<EventLogJPA, EventLogIdJPA> {}
