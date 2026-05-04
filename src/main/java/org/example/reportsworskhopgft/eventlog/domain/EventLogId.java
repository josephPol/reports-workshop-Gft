package org.example.reportsworskhopgft.eventlog.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class EventLogId {
    private UUID value;

    public static EventLogId generate() {
        return new EventLogId(UUID.randomUUID());
    }

}
