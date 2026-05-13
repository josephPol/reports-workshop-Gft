package org.example.reportsworskhopgft.eventlog.application;

import java.util.List;
import org.example.reportsworskhopgft.eventlog.application.projections.OrderHistoryProjection;
import org.example.reportsworskhopgft.eventlog.application.projections.SystemStatsProjection;
import org.example.reportsworskhopgft.eventlog.domain.EventLog;
import org.example.reportsworskhopgft.eventlog.domain.EventLogId;

public interface EventLogService {
    List<EventLog> findAllEventsLogs();

    EventLog findEventLogById(EventLogId id);

    void save(EventLog eventLog);

    List<OrderHistoryProjection> getOrderHistory();

    SystemStatsProjection getSystemStats();
}
