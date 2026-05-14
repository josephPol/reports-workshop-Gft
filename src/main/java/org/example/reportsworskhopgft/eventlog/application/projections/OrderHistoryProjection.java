package org.example.reportsworskhopgft.eventlog.application.projections;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Last state of the order")
public record OrderHistoryProjection(
        @Schema(description = "ID of the order", example = "ORD-9982")
        String orderId,
        @Schema(description = "ID of the factory", example = "FAC-A")
        String factoryId,
        @Schema(description = "State of the order", example = "COMPLETED")
        String status,
        @Schema(description = "Simulated day of the last update", example = "5")
        int lastUpdateDay
) {}