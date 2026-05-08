package org.example.reportsworskhopgft.eventlog.infrastructure;

import org.example.reportsworskhopgft.eventlog.domain.EventLog;
import org.example.reportsworskhopgft.eventlog.application.EventLogRepository;
import lombok.RequiredArgsConstructor;
import org.example.reportsworskhopgft.eventlog.domain.EventLogId;
import org.example.reportsworskhopgft.eventlog.infrastructure.EventLogRepositoryJPA;
import org.example.reportsworskhopgft.eventlog.infrastructure.persistence.EventLogJPA;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EventLogRepositoryImpl implements EventLogRepository {

    private final EventLogRepositoryJPA jpa;

    @Override
    public List<EventLogJPA> findAllEventsLogs() {
        return List.of();
    }

    @Override
    public EventLogJPA findEventLogById(Integer id) {
        return null;
    }

    @Override
    public void save(EventLog eventLog) {
        jpa.save(toJPA(eventLog));
    }

    private EventLogJPA toJPA(EventLog eventLog) {
        return new EventLogJPA(
                EventLogId.generate(),
                eventLog.getEventType(),
                eventLog.getSourceService(),
                eventLog.getPayload(),
                eventLog.getSimulationDay(),
                eventLog.getOccurredAt()
        );
    }

}
