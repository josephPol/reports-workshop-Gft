package org.example.reportsworskhopgft.eventlog.infrastructure;

import org.example.reportsworskhopgft.eventlog.domain.EventLog;
import org.example.reportsworskhopgft.eventlog.domain.EventType;
import org.example.reportsworskhopgft.eventlog.domain.SourceService;
import org.example.reportsworskhopgft.eventlog.infrastructure.persistence.EventLogJPA;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EventLogRepositoryImplTest {

    @Mock
    private EventLogRepositoryJPA eventLogRepositoryJPA;

    @InjectMocks
    private EventLogRepositoryImpl eventLogRepository;

    @Test
    void should_return_empty_list_when_findAllEventsLogs_is_called() {
        List<EventLogJPA> result = eventLogRepository.findAllEventsLogs();

        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
    }

    @Test
    void should_return_null_when_findEventLogById_is_called() {
        EventLogJPA result = eventLogRepository.findEventLogById(1);

        assertThat(result).isNull();
    }

    @Test
    void should_map_and_save_event_log() {
        EventLog eventLog = EventLog.builder()
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
        assertThat(saved.getEventType()).isEqualTo(eventLog.getEventType());
        assertThat(saved.getSourceService()).isEqualTo(eventLog.getSourceService());
        assertThat(saved.getPayload()).isEqualTo(eventLog.getPayload());
        assertThat(saved.getSimulationDay()).isEqualTo(eventLog.getSimulationDay());
        assertThat(saved.getOccurredAt()).isEqualTo(eventLog.getOccurredAt());
    }
}
