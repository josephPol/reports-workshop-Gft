package org.example.reportsworskhopgft.eventlog.infrastructure;

import org.example.reportsworskhopgft.eventlog.application.EventLogService;
import org.example.reportsworskhopgft.eventlog.domain.EventLog;
import org.example.reportsworskhopgft.eventlog.domain.EventLogRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class EventLogServiceTest {

    @Test
    void shouldCreateAndSaveEventLog() {

        EventLogRepository repositoryMock = mock(EventLogRepository.class);
        EventLogService service = new EventLogService(repositoryMock);


        service.save(
                "TRUCK_REGISTERED",
                "TRANSPORT",
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