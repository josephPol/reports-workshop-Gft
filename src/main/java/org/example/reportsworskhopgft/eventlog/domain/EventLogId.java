package org.example.reportsworskhopgft.eventlog.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public record EventLogId(String value) {

    public EventLogId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("EventLogId cannot be blank");
        }
    }

    public static EventLogId generate() {
        return new EventLogId(java.util.UUID.randomUUID().toString());
    }

    public java.util.UUID toUUID() {
        return java.util.UUID.fromString(this.value);
    }

    public String getValue() {
        return this.value;
    }
}
