package org.example.reportsworskhopgft.eventlog.application.exception;

import org.example.reportsworskhopgft.eventlog.domain.EventLogId;

public class EventLogNotFoundException extends RuntimeException {

    private final EventLogId id;

    public EventLogNotFoundException(EventLogId id) {
        super("EventLog not found: " + (id == null ? "null" : id.value()));
        this.id = id;
    }

    public EventLogId getId() {
        return id;
    }
}
