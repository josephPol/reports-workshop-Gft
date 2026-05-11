package org.example.reportsworskhopgft.eventlog.infrastructure;

import org.example.reportsworskhopgft.eventlog.application.EventLogRepository;
import org.example.reportsworskhopgft.eventlog.domain.EventLog;
import org.example.reportsworskhopgft.eventlog.domain.EventLogId;
import org.example.reportsworskhopgft.eventlog.domain.EventType;
import org.example.reportsworskhopgft.eventlog.domain.SourceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventLogControllerTest {

    @Mock
    private EventLogRepository eventLogRepository;

    @InjectMocks
    private EventLogController controller;

    @Test
    void shouldReturnAllEvents() {
        List<EventLog> events = List.of(new EventLog());
        when(eventLogRepository.findAllEventsLogs()).thenReturn(events);

        List<EventLog> response = controller.getAllEventLogs();

        assertEquals(events, response);
    }

    @Test
    void shouldReturnEventById() {
        EventLogId id = EventLogId.generate();
        EventLog expected = EventLog.builder()
                .id(id)
                .eventType(EventType.TRUCK_REGISTERED)
                .sourceService(SourceService.TRANSPORT)
                .payload("{\"ok\":true}")
                .simulationDay(1)
                .occurredAt("2026-05-11T11:00:00")
                .build();
        when(eventLogRepository.findEventLogById(id)).thenReturn(expected);

        EventLog response = controller.getEventLogById(id);

        assertEquals(expected, response);
    }
}
