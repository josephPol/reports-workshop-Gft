package org.example.reportsworskhopgft.eventlog.application;

import org.example.reportsworskhopgft.eventlog.application.projections.OrderHistoryProjection;
import org.example.reportsworskhopgft.eventlog.application.projections.SystemStatsProjection;
import java.util.List;

public interface ReportService {
    List<OrderHistoryProjection> getOrderHistory();
    SystemStatsProjection getSystemStats();
}