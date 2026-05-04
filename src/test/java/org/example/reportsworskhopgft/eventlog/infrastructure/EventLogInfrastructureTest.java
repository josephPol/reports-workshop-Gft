package org.example.reportsworskhopgft.eventlog.infrastructure;

import org.example.reportsworskhopgft.eventlog.domain.EventLog;
import org.example.reportsworskhopgft.eventlog.domain.EventLogRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EventLogInfrastructureTest {

    @Test
    void shouldCreateControllerAdapter() {
        assertNotNull(new EventLogController());
    }

    @Test
    void shouldPersistCreatedEventLogThroughRepository() {
        CapturingEventLogRepository repository = new CapturingEventLogRepository();
        EventLogService service = new EventLogService(repository);

        service.save(
                "DELIVERY_CREATED",
                "REPORTING",
                "{\"payload\":true}",
                7,
                "2026-05-04T12:00:00"
        );

        assertNotNull(repository.savedEventLog);
        assertEquals("DELIVERY_CREATED", repository.savedEventLog.getEventType());
        assertEquals("REPORTING", repository.savedEventLog.getSourceService());
        assertEquals("{\"payload\":true}", repository.savedEventLog.getPayload());
        assertEquals(7, repository.savedEventLog.getSimulationDay());
        assertEquals("2026-05-04T12:00:00", repository.savedEventLog.getOccurredAt());
    }

    private static final class CapturingEventLogRepository implements EventLogRepository {
        private EventLog savedEventLog;

        @Override
        public void save(EventLog eventLog) {
            this.savedEventLog = eventLog;
        }
    }
}
