package org.example.reportsworskhopgft.eventlog.infrastructure;

import org.example.reportsworskhopgft.eventlog.infrastructure.persistence.EventLogJPA;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class EventLogRepositoryImplTest {

    @Mock
    private EventLogRepositoryJPA eventLogRepositoryJPA;

    @InjectMocks
    private EventLogRepositoryImpl eventLogRepository;

    @Test
    void should_return_null_when_findAllEventsLogs_is_called() {
        ArrayList<EventLogJPA> result = eventLogRepository.findAllEventsLogs();

        assertThat(result).isNull();
    }

    @Test
    void should_return_null_when_findEventLogById_is_called() {
        EventLogJPA result = eventLogRepository.findEventLogById(1);

        assertThat(result).isNull();
    }
}