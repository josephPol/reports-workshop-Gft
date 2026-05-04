package org.example.reportsworskhopgft.eventlog.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventLog {
    private EventLogId id;
    private EventType eventType;
    private SourceService sourceService;
    private String payload;
    private int simulationDay;
    private String ocurredAt;
}
