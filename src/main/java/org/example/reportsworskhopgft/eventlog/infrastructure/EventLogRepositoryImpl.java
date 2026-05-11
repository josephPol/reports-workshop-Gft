package org.example.reportsworskhopgft.eventlog.infrastructure;

import org.example.reportsworskhopgft.eventlog.application.EventLogRepository;
import org.example.reportsworskhopgft.eventlog.domain.EventLog;
import org.example.reportsworskhopgft.eventlog.domain.EventLogId;
import org.example.reportsworskhopgft.eventlog.infrastructure.persistence.EventLogJPA;
import org.example.reportsworskhopgft.eventlog.infrastructure.persistence.EventLogIdJPA;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EventLogRepositoryImpl implements EventLogRepository {

    private final EventLogRepositoryJPA jpaRepository;

    public EventLogRepositoryImpl(EventLogRepositoryJPA jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<EventLog> findAllEventsLogs() {
        return jpaRepository.findAll().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public EventLog findEventLogById(EventLogId id) {
        EventLogIdJPA jpaId = new EventLogIdJPA(id.value());
        return jpaRepository.findById(jpaId).map(this::mapToDomain).orElse(null);
    }

    @Override
    public void save(EventLog eventLog) {
        EventLogJPA jpaEntity = mapToJPA(eventLog);
        jpaRepository.save(jpaEntity);
    }

    private EventLog mapToDomain(EventLogJPA jpaEntity) {
        return new EventLog(
                new EventLogId(jpaEntity.getId().getValue()),
                jpaEntity.getEventType(),
                jpaEntity.getSourceService(),
                jpaEntity.getPayload(),
                jpaEntity.getSimulationDay(),
                jpaEntity.getOccurredAt()
        );
    }

    private EventLogJPA mapToJPA(EventLog domain) {
        EventLogIdJPA jpaId = new EventLogIdJPA(domain.getId().value());
        return new EventLogJPA(
                jpaId,
                domain.getEventType(),
                domain.getSourceService(),
                domain.getPayload(),
                domain.getSimulationDay(),
                domain.getOccurredAt()
        );
    }
}