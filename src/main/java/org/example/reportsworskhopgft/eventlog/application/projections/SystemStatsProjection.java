package org.example.reportsworskhopgft.eventlog.application.projections;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Metrics in real time")
public record SystemStatsProjection(
        @Schema(description = "Total of orders", example = "150")
        int totalOrders,
        @Schema(description = "Orders completed", example = "85")
        int completedOrders,
        @Schema(description = "Orders blocked", example = "3")
        int blockedOrders,
        @Schema(description = "Trucks in transit", example = "12")
        int trucksInTransit
) {}