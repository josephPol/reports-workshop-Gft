package org.example.reportsworskhopgft.eventlog.domain;

public class EventLog {

    private final EventLogId id;
    private final String eventType;
    private final String sourceService;
    private final String payload;
    private final int simulationDay;
    private final String occurredAt;

    private EventLog(EventLogId id, String eventType, String sourceService,
                     String payload, int simulationDay, String occurredAt) {
        this.id = id;
        this.eventType = eventType;
        this.sourceService = sourceService;
        this.payload = payload;
        this.simulationDay = simulationDay;
        this.occurredAt = occurredAt;
    }

    public static EventLog create(String eventType, String sourceService,
                                  String payload, int simulationDay, String occurredAt) {
        return new EventLog(
                EventLogId.generate(),
                eventType,
                sourceService,
                payload,
                simulationDay,
                occurredAt
        );
    }

    public EventLogId getId() { return id; }
    public String getEventType() { return eventType; }
    public String getSourceService() { return sourceService; }
    public String getPayload() { return payload; }
    public int getSimulationDay() { return simulationDay; }
    public String getOccurredAt() { return occurredAt; }
}
//