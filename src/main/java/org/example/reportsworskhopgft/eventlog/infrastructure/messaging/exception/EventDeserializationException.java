package org.example.reportsworskhopgft.eventlog.infrastructure.messaging.exception;

public class EventDeserializationException extends EventConsumerException {

    public EventDeserializationException(String eventName, String message, Throwable cause) {
        super(eventName, message, cause);
    }
}
