package org.example.reportsworskhopgft.eventlog.domain;

import lombok.*;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode  //  para que assertEquals(left, right) funcione
@Builder
public class EventLog {

    private UUID id;
    private EventType eventType;
    private SourceService sourceService;
    private String payload;
    private Integer simulationDay;
    private String occurredAt;

}