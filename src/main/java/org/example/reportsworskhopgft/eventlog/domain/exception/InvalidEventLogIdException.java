package org.example.reportsworskhopgft.eventlog.domain.exception;

public class InvalidEventLogIdException extends IllegalArgumentException {

    public InvalidEventLogIdException(String value) {
        super("EventLogId cannot be blank");
    }
}
