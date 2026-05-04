package org.example.reportsworskhopgft.eventlog.domain;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EventLogDomainTest {

    @Test
    void shouldGenerateAnIdentifier() {
        EventLogId generated = EventLogId.generate();

        assertNotNull(generated);
        assertNotNull(generated.getValue());
    }

    @Test
    void shouldExposeIdentifierStateThroughLombokGeneratedMethods() {
        UUID uuid = UUID.randomUUID();
        EventLogId left = new EventLogId(uuid);
        EventLogId right = new EventLogId();
        right.setValue(uuid);

        assertEquals(uuid, left.getValue());
        assertEquals(left, right);
        assertEquals(left.hashCode(), right.hashCode());
        assertTrue(left.toString().contains(uuid.toString()));
    }

    @Test
    void shouldExposeEventLogStateThroughLombokGeneratedMethods() {
        EventLogId eventLogId = new EventLogId(UUID.randomUUID());
        EventLog eventLog = new EventLog(
                eventLogId,
                EventType.DELIVERY_CREATED,
                SourceService.REPORTING,
                "{\"payload\":true}",
                7,
                "2026-05-04T12:00:00"
        );

        assertEquals(eventLogId, eventLog.getId());
        assertEquals(EventType.DELIVERY_CREATED, eventLog.getEventType());
        assertEquals(SourceService.REPORTING, eventLog.getSourceService());
        assertEquals("{\"payload\":true}", eventLog.getPayload());
        assertEquals(7, eventLog.getSimulationDay());
        assertEquals("2026-05-04T12:00:00", eventLog.getOcurredAt());
        assertTrue(eventLog.toString().contains("DELIVERY_CREATED"));
    }

    @Test
    void shouldCompareEventLogsByValue() {
        EventLogId eventLogId = new EventLogId(UUID.randomUUID());
        EventLog left = new EventLog(
                eventLogId,
                EventType.DELIVERY_CREATED,
                SourceService.REPORTING,
                "{\"payload\":true}",
                7,
                "2026-05-04T12:00:00"
        );
        EventLog right = new EventLog();
        right.setId(eventLogId);
        right.setEventType(EventType.DELIVERY_CREATED);
        right.setSourceService(SourceService.REPORTING);
        right.setPayload("{\"payload\":true}");
        right.setSimulationDay(7);
        right.setOcurredAt("2026-05-04T12:00:00");

        assertEquals(left, right);
        assertEquals(left.hashCode(), right.hashCode());

        right.setSimulationDay(8);

        assertNotEquals(left, right);
    }

    @Test
    void shouldExposeAvailableDomainEnums() {
        assertEquals(EventType.TIME_ADVANCED, EventType.valueOf("TIME_ADVANCED"));
        assertEquals(EventType.WAREHOUSE_STOCK_CHANGED, EventType.values()[EventType.values().length - 1]);
        assertEquals(SourceService.FACTORY, SourceService.valueOf("FACTORY"));
        assertEquals(SourceService.REPORTING, SourceService.values()[SourceService.values().length - 1]);
    }
}
