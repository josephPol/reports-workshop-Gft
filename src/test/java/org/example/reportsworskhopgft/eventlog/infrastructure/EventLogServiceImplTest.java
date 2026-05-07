package org.example.reportsworskhopgft.eventlog.infrastructure;
import org.example.reportsworskhopgft.eventlog.application.EventLogServiceImpl;

import org.example.reportsworskhopgft.eventlog.domain.EventLog;
import org.example.reportsworskhopgft.eventlog.domain.EventType;
import org.example.reportsworskhopgft.eventlog.domain.SourceService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class EventLogServiceImplTest {

    @Test
    void shouldCreateAndSaveEventLog() {

        EventLogRepository repositoryMock = mock(EventLogRepository.class);
        EventLogServiceImpl service = new EventLogServiceImpl(repositoryMock);


        service.save(
                EventType.TRUCK_REGISTERED,
                SourceService.TRANSPORT,
                "{\"test\":\"data\"}",
                1,
                "2026-05-06T10:00:00"
        );


        ArgumentCaptor<EventLog> eventCaptor = ArgumentCaptor.forClass(EventLog.class);
        verify(repositoryMock).save(eventCaptor.capture());

        EventLog savedEvent = eventCaptor.getValue();
        assertNotNull(savedEvent);
        assertEquals("TRUCK_REGISTERED", savedEvent.getEventType().name());
        assertEquals("TRANSPORT", savedEvent.getSourceService().name());
        assertEquals(1, savedEvent.getSimulationDay());
    }
}