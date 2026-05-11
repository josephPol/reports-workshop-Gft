package org.example.reportsworskhopgft.eventlog.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.example.reportsworskhopgft.eventlog.domain.EventLog;
import org.example.reportsworskhopgft.eventlog.application.EventLogRepository;
import lombok.RequiredArgsConstructor;
import org.example.reportsworskhopgft.eventlog.domain.EventLogId;
import org.example.reportsworskhopgft.eventlog.infrastructure.EventLogRepositoryJPA;
import org.example.reportsworskhopgft.eventlog.infrastructure.persistence.EventLogJPA;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class EventLogRepositoryImpl implements EventLogRepository {

    private final EventLogRepositoryJPA jpa;

    @Override
    public List<EventLogJPA> findAllEventsLogs() {
        log.info("Events found:" + jpa.findAll().size());
        return jpa.findAll();
    }

    @Override
    public EventLogJPA findEventLogById(EventLogId id) {
        Optional<EventLogJPA>eventLogJPAOptional = jpa.findById(id);
        return eventLogJPAOptional.orElse(null);
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
