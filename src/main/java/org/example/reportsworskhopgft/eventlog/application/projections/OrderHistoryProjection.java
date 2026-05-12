package org.example.reportsworskhopgft.eventlog.application.projections;

public record OrderHistoryProjection(
        String orderId,
        String factoryId,
        String status,
        int simulationDay
) {}