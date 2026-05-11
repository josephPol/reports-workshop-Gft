package org.example.reportsworskhopgft.eventlog.infrastructure;

import org.example.reportsworskhopgft.eventlog.application.impl.EventLogServiceImpl;
import org.example.reportsworskhopgft.eventlog.domain.EventType;
import org.example.reportsworskhopgft.eventlog.domain.SourceService;
import org.example.reportsworskhopgft.eventlog.infrastructure.persistence.EventLogJPA;
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
        EventLogServiceImpl service = new EventLogServiceImpl(repositoryMock);


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
    void should_return_all_events_when_findAll_is_called() {
        EventLogRepositoryJPA repositoryMock = mock(EventLogRepositoryJPA.class);
        EventLogServiceImpl service = new EventLogServiceImpl(repositoryMock);

        List<EventLogJPA> expected = List.of(new EventLogJPA());
        when(repositoryMock.findAll()).thenReturn(expected);

        List<EventLogJPA> result = service.findAll();

        assertEquals(expected, result);
    }
}
