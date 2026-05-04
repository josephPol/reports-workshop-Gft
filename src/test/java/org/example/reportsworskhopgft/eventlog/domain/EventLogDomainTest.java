package org.example.reportsworskhopgft.eventlog.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EventLogDomainTest {

    @Test
    void shouldGenerateAnIdentifier() {
        EventLogId generated = EventLogId.generate();

        assertNotNull(generated);
        assertNotNull(generated.value());
    }

    @Test
    void shouldRejectBlankIdentifierValues() {
        assertThrows(IllegalArgumentException.class, () -> new EventLogId(" "));
        assertThrows(IllegalArgumentException.class, () -> new EventLogId(null));
    }

    @Test
    void shouldExposeEventLogState() {
        EventLog eventLog = EventLog.create(
                EventType.DELIVERY_CREATED.name(),
                SourceService.REPORTING.name(),
                "{\"payload\":true}",
                7,
                "2026-05-04T12:00:00"
        );

        assertNotNull(eventLog.getId());
        assertEquals(EventType.DELIVERY_CREATED.name(), eventLog.getEventType());
        assertEquals(SourceService.REPORTING.name(), eventLog.getSourceService());
        assertEquals("{\"payload\":true}", eventLog.getPayload());
        assertEquals(7, eventLog.getSimulationDay());
        assertEquals("2026-05-04T12:00:00", eventLog.getOccurredAt());
    }

    @Test
    void shouldExposeAvailableDomainEnums() {
        assertEquals(EventType.TIME_ADVANCED, EventType.valueOf("TIME_ADVANCED"));
        assertEquals(EventType.WAREHOUSE_STOCK_CHANGED, EventType.values()[EventType.values().length - 1]);
        assertEquals(SourceService.FACTORY, SourceService.valueOf("FACTORY"));
        assertEquals(SourceService.REPORTING, SourceService.values()[SourceService.values().length - 1]);
    }
}
