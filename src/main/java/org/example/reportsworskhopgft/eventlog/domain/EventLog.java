package org.example.reportsworskhopgft.eventlog.domain;

import lombok.*;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventLog {

    private UUID id;
    private EventType eventType;
    private SourceService sourceService;
    private String payload;
    private Integer simulationDay;
    private String occurredAt;

}