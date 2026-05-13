package org.example.reportsworskhopgft.eventlog.application.projections;

public record SystemStatsProjection(
        int totalOrders, int completedOrders, int blockedOrders, int trucksInTransit) {}
