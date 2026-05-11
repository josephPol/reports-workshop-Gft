package org.example.reportsworskhopgft.blockedorder.domain;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder

public class BlockedOrder {

    private String orderId;
    private String factoryId;
    private String reason;
    private Integer blockedSinceDay;

}
