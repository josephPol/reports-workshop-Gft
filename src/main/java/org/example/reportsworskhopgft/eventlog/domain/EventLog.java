package org.example.reportsworskhopgft.eventlog.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "event_log")
public class EventLog {

    @EmbeddedId
    @Column(name = "id", nullable = false)
    private EventLogId id;

    @Size(max = 100)
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false, length = 100)
    private EventType eventType;

    @Size(max = 100)
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "source_service", nullable = false, length = 100)
    private SourceService sourceService;

    @NotNull
    @Column(name = "payload", nullable = false, length = Integer.MAX_VALUE)
    private String payload;

    @NotNull
    @Column(name = "simulation_day", nullable = false)
    private Integer simulationDay;

    @Size(max = 50)
    @NotNull
    @Column(name = "occurred_at", nullable = false, length = 50)
    private String occurredAt;

}