package org.example.reportsworskhopgft.blockedorder.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "blocked_order")
public class BlockedOrder {
    @Id
    @Size(max = 100)
    @Column(name = "order_id", nullable = false, length = 100)
    private String orderId;

    @Size(max = 100)
    @NotNull
    @Column(name = "factory_id", nullable = false, length = 100)
    private String factoryId;

    @NotNull
    @Column(name = "reason", nullable = false, length = Integer.MAX_VALUE)
    private String reason; 

    @NotNull
    @Column(name = "blocked_since_day", nullable = false)
    private Integer blockedSinceDay;

}
