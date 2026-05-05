package org.example.reportsworskhopgft.eventlog.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "event_logs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventLog {

    @EmbeddedId
    private EventLogId id;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Enumerated(EnumType.STRING)
    private SourceService sourceService;

    @Column(columnDefinition = "TEXT")
    private String payload;

    private int simulationDay;

    private String ocurredAt;
}