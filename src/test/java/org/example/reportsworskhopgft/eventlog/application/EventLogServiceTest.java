package org.example.reportsworskhopgft.eventlog.application;

import org.example.reportsworskhopgft.eventlog.domain.EventLogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class EventLogServiceTest {

    @Mock
    private EventLogRepository repository;

    @InjectMocks
    private EventLogService eventLogService;

    @Test
    void should_save_event_log() {
        eventLogService.save(
                "time.advanced.v1",
                "time",
                "{\"simulationDay\":3}",
                3,
                "2024-01-01T00:00:00Z"
        );

        verify(repository, times(1)).save(any());
    }
}