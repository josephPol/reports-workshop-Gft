package org.example.reportsworskhopgft.eventlog.infrastructure;

import lombok.RequiredArgsConstructor;
import org.example.reportsworskhopgft.eventlog.application.EventLogRepository;
import org.example.reportsworskhopgft.eventlog.infrastructure.persistence.EventLogJPA;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class EventLogRepositoryImpl implements EventLogRepository {

    private final EventLogRepositoryJPA eventLogRepositoryJPA;

    @Override
    public ArrayList<EventLogJPA> findAllEventsLogs() {
        return null;
    }

    @Override
    public EventLogJPA findEventLogById(Integer id) {
        return null;
    }
}
