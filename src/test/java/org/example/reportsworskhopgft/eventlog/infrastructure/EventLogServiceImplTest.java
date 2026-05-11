package org.example.reportsworskhopgft.eventlog.infrastructure;

import org.example.reportsworskhopgft.eventlog.application.impl.EventLogServiceImpl;
import org.example.reportsworskhopgft.eventlog.domain.EventLog;
import org.example.reportsworskhopgft.eventlog.domain.EventLogId;
import org.example.reportsworskhopgft.eventlog.domain.EventType;
import org.example.reportsworskhopgft.eventlog.domain.SourceService;
import org.example.reportsworskhopgft.eventlog.infrastructure.persistence.EventLogJPA;
import org.example.reportsworskhopgft.eventlog.infrastructure.persistence.EventLogIdJPA;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventLogServiceImplTest {

    @Mock
    private EventLogRepositoryJPA eventLogRepositoryJPA;

    @InjectMocks
    private EventLogServiceImpl eventLogRepository;

    @Test
    void should_return_empty_list_when_findAllEventsLogs_is_called() {
        when(eventLogRepositoryJPA.findAll()).thenReturn(List.of());

        List<EventLog> result = eventLogRepository.findAllEventsLogs();

        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
    }

    @Test
    void should_map_jpa_entities_to_domain_when_findAllEventsLogs_is_called() {
        EventLogIdJPA id = new EventLogIdJPA("id-1");
        EventLogJPA jpaEntity = new EventLogJPA(
                id,
                EventType.DELIVERY_CREATED,
                SourceService.REPORTING,
                "{\"payload\":true}",
                7,
                "2026-05-04T12:00:00"
        );
        when(eventLogRepositoryJPA.findAll()).thenReturn(List.of(jpaEntity));

        List<EventLog> result = eventLogRepository.findAllEventsLogs();

        assertThat(result).hasSize(1);
        EventLog mapped = result.get(0);
        assertThat(mapped.getId()).isEqualTo(new EventLogId(id.getValue()));
        assertThat(mapped.getEventType()).isEqualTo(jpaEntity.getEventType());
        assertThat(mapped.getSourceService()).isEqualTo(jpaEntity.getSourceService());
        assertThat(mapped.getPayload()).isEqualTo(jpaEntity.getPayload());
        assertThat(mapped.getSimulationDay()).isEqualTo(jpaEntity.getSimulationDay());
        assertThat(mapped.getOccurredAt()).isEqualTo(jpaEntity.getOccurredAt());
    }

    @Test
    void should_return_null_when_findEventLogById_is_called() {
        EventLogId nonExistentId = new EventLogId("non-existent-uuid");
        when(eventLogRepositoryJPA.findById(new EventLogIdJPA(nonExistentId.value()))).thenReturn(Optional.empty());

        EventLog result = eventLogRepository.findEventLogById(nonExistentId);

        assertThat(result).isNull();
    }

    @Test
    void should_return_mapped_event_when_findEventLogById_is_called_and_entity_exists() {
        EventLogId id = new EventLogId("id-2");
        EventLogJPA jpaEntity = new EventLogJPA(
                new EventLogIdJPA(id.value()),
                EventType.TRUCK_REGISTERED,
                SourceService.TRANSPORT,
                "{\"truckId\":\"1\"}",
                2,
                "2026-05-07T18:00:00"
        );
        when(eventLogRepositoryJPA.findById(new EventLogIdJPA(id.value()))).thenReturn(Optional.of(jpaEntity));

        EventLog result = eventLogRepository.findEventLogById(id);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getEventType()).isEqualTo(jpaEntity.getEventType());
        assertThat(result.getSourceService()).isEqualTo(jpaEntity.getSourceService());
        assertThat(result.getPayload()).isEqualTo(jpaEntity.getPayload());
        assertThat(result.getSimulationDay()).isEqualTo(jpaEntity.getSimulationDay());
        assertThat(result.getOccurredAt()).isEqualTo(jpaEntity.getOccurredAt());
    }

    @Test
    void should_map_and_save_event_log() {
        EventLogId id = EventLogId.generate();
        EventLog eventLog = EventLog.builder()
                .id(id)
                .eventType(EventType.TRUCK_POSITION_UPDATED)
                .sourceService(SourceService.TRANSPORT)
                .payload("{\"ok\":true}")
                .simulationDay(3)
                .occurredAt("2026-05-08T10:00:00")
                .build();

        eventLogRepository.save(eventLog);

        ArgumentCaptor<EventLogJPA> captor = ArgumentCaptor.forClass(EventLogJPA.class);
        verify(eventLogRepositoryJPA).save(captor.capture());

        EventLogJPA saved = captor.getValue();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getId().getValue()).isEqualTo(id.value());
        assertThat(saved.getEventType()).isEqualTo(eventLog.getEventType());
        assertThat(saved.getSourceService()).isEqualTo(eventLog.getSourceService());
        assertThat(saved.getPayload()).isEqualTo(eventLog.getPayload());
        assertThat(saved.getSimulationDay()).isEqualTo(eventLog.getSimulationDay());
        assertThat(saved.getOccurredAt()).isEqualTo(eventLog.getOccurredAt());
    }
}
