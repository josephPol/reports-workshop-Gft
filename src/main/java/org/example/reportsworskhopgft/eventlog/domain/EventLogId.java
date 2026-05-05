package org.example.reportsworskhopgft.eventlog.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class EventLogId implements Serializable {
    private UUID value;

    public static EventLogId generate() {
        return new EventLogId(UUID.randomUUID());
    }

}
