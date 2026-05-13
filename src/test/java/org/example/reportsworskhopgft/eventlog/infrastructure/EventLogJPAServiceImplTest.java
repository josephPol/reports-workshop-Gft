package org.example.reportsworskhopgft.eventlog.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.reportsworskhopgft.eventlog.application.impl.EventLogServiceImpl;
import org.example.reportsworskhopgft.eventlog.domain.EventLog;
import org.example.reportsworskhopgft.eventlog.domain.EventLogId;
import org.example.reportsworskhopgft.eventlog.domain.EventType;
import org.example.reportsworskhopgft.eventlog.domain.SourceService;
import org.example.reportsworskhopgft.eventlog.infrastructure.persistence.EventLogJPA;
import org.example.reportsworskhopgft.eventlog.infrastructure.persistence.EventLogIdJPA;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class EventLogJPAServiceImplTest {

    @Test
    void shouldCreateAndSaveEventLog() {

        EventLogRepositoryJPA repositoryMock = mock(EventLogRepositoryJPA.class);
        ObjectMapper objectMapperMock = mock(ObjectMapper.class);
        EventLogServiceImpl service = new EventLogServiceImpl(repositoryMock, objectMapperMock);


        service.save(
                EventType.TRUCK_REGISTERED,
                SourceService.TRANSPORT,
                "{\"test\":\"data\"}",
                1,
                "2026-05-06T10:00:00"
        );


        ArgumentCaptor<EventLogJPA> eventCaptor = ArgumentCaptor.forClass(EventLogJPA.class);
        verify(repositoryMock).save(eventCaptor.capture());

        EventLogJPA savedEvent = eventCaptor.getValue();
        assertNotNull(savedEvent);
        assertEquals("TRUCK_REGISTERED", savedEvent.getEventType().name());
        assertEquals("TRANSPORT", savedEvent.getSourceService().name());
        assertEquals(1, savedEvent.getSimulationDay());
    }

    @Test
    void should_return_all_events_when_findAllEventsLogs_is_called() {
        EventLogRepositoryJPA repositoryMock = mock(EventLogRepositoryJPA.class);
        ObjectMapper objectMapperMock = mock(ObjectMapper.class);
        EventLogServiceImpl service = new EventLogServiceImpl(repositoryMock, objectMapperMock);

        EventLogIdJPA id = new EventLogIdJPA("id-1");
        EventLogJPA jpa = new EventLogJPA(
                id,
                EventType.TIME_ADVANCED,
                SourceService.TIME,
                "{\"ok\":true}",
                3,
                "2026-05-11T12:00:00"
        );
        when(repositoryMock.findAll()).thenReturn(List.of(jpa));

        List<EventLog> result = service.findAllEventsLogs();

        assertEquals(1, result.size());
        EventLog mapped = result.get(0);
        assertEquals(new EventLogId("id-1"), mapped.getId());
        assertEquals(EventType.TIME_ADVANCED, mapped.getEventType());
        assertEquals(SourceService.TIME, mapped.getSourceService());
        assertEquals("{\"ok\":true}", mapped.getPayload());
        assertEquals(3, mapped.getSimulationDay());
        assertEquals("2026-05-11T12:00:00", mapped.getOccurredAt());
    }
}
