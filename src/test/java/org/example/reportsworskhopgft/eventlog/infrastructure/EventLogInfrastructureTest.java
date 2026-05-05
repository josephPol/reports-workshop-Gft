package org.example.reportsworskhopgft.eventlog.infrastructure;

import org.example.reportsworskhopgft.eventlog.infrastructure.persistence.EventLogRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class EventLogInfrastructureTest {

    @Test
    void shouldCreateControllerAndRepositoryAdapters() {
        assertNotNull(new EventLogController());
        assertNotNull(new EventLogRepository());
    }
}
