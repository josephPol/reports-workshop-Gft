package org.example.reportsworskhopgft.eventlog.application;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class EventLogServiceTest {

    @Test
    void shouldCreateApplicationServiceInstance() {
        assertNotNull(new EventLogService());
    }
}
