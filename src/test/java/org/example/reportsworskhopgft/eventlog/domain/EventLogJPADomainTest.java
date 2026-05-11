package org.example.reportsworskhopgft.eventlog.domain;

import org.example.reportsworskhopgft.eventlog.infrastructure.persistence.EventLogJPA;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventLogJPADomainTest {

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
        EventLogJPA eventLogJPA = new EventLogJPA(
                EventLogId.generate(),
                EventType.DELIVERY_CREATED,
                SourceService.REPORTING,
                "{\"payload\":true}",
                7,
                "2026-05-04T12:00:00"
        );

        assertNotNull(eventLogJPA.getId());
        assertEquals(EventType.DELIVERY_CREATED, eventLogJPA.getEventType());
        assertEquals(SourceService.REPORTING, eventLogJPA.getSourceService());
        assertEquals("{\"payload\":true}", eventLogJPA.getPayload());
        assertEquals(7, eventLogJPA.getSimulationDay());
        assertEquals("2026-05-04T12:00:00", eventLogJPA.getOccurredAt());
    }

    @Test
    void shouldExposeAvailableDomainEnums() {
        assertEquals(EventType.TIME_ADVANCED, EventType.valueOf("TIME_ADVANCED"));
        assertEquals(EventType.WAREHOUSE_STOCK_CHANGED, EventType.values()[EventType.values().length - 1]);
        assertEquals(SourceService.FACTORY, SourceService.valueOf("FACTORY"));
        assertEquals(SourceService.REPORTING, SourceService.values()[SourceService.values().length - 1]);
    }
}
