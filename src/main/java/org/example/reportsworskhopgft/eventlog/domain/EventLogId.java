package org.example.reportsworskhopgft.eventlog.domain;

    public record EventLogId(String value) {

        public EventLogId {
            if (value == null || value.isBlank()) {
                throw new IllegalArgumentException("EventLogId cannot be blank");
            }
        }

        public static EventLogId generate() {
            return new EventLogId(java.util.UUID.randomUUID().toString());
        }
    }