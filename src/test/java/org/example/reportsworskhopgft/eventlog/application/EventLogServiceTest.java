package org.example.reportsworskhopgft.eventlog.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.reportsworskhopgft.eventlog.domain.EventLog;
import org.example.reportsworskhopgft.eventlog.infrastructure.persistence.EventLogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventLogServiceTest {

    @Mock
    private EventLogRepository repository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private EventLogService eventLogService;

    @Test
    void shouldLogEventSuccessfully() throws Exception {

        TruckRegisteredEvent.Position position = new TruckRegisteredEvent.Position(10, 20);
        TruckRegisteredEvent event = new TruckRegisteredEvent(UUID.randomUUID(), "Camión Alpha", position, 50, 1);


        when(objectMapper.writeValueAsString(event)).thenReturn("{\"fake\":\"json\"}");


        eventLogService.logEvent(event);


        verify(repository, times(1)).save(any(EventLog.class));
    }

    @Test
    void shouldHandleExceptionWhenLoggingEventFails() throws Exception {

        TruckRegisteredEvent event = new TruckRegisteredEvent(UUID.randomUUID(), "Camión Beta", null, 50, 1);


        when(objectMapper.writeValueAsString(event)).thenThrow(new RuntimeException("Error de serialización"));


        eventLogService.logEvent(event);

        verify(repository, never()).save(any(EventLog.class));
    }
}