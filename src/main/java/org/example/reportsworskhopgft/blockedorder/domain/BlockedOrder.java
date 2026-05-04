package org.example.reportsworskhopgft.blockedorder.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlockedOrder {
    private String orderId;  // No esta claro de momento.
    private String factoryId; // Me han dicho los de factory que sera un String.
    private String reason;
    private int blockedSinceDay;
}
