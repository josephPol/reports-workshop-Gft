package org.example.reportsworskhopgft.eventlog.infrastructure;

import org.example.reportsworskhopgft.eventlog.application.EventLogRepository;
import org.example.reportsworskhopgft.eventlog.domain.EventLogId;
import org.example.reportsworskhopgft.eventlog.infrastructure.persistence.EventLogJPA;
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
        List<EventLogJPA> events = List.of();
        when(eventLogRepository.findAllEventsLogs()).thenReturn(events);

        List<EventLogJPA> response = controller.getAllEventLogs();

        assertEquals(events, response);
    }

    @Test
    void shouldReturnEventById() {
        EventLogId id = EventLogId.generate();
        EventLogJPA expected = new EventLogJPA();
        when(eventLogRepository.findEventLogById(id)).thenReturn(expected);

        EventLogJPA response = controller.getEventLogById(id);

        assertEquals(expected, response);
    }
}
