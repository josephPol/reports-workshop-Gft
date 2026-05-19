package org.example.reportsworkshopgft.eventlog.application;

import java.util.List;
import org.example.reportsworkshopgft.eventlog.application.projections.OrderHistoryProjection;
import org.example.reportsworkshopgft.eventlog.application.projections.SystemStatsProjection;
import org.example.reportsworkshopgft.eventlog.domain.EventLog;
import org.example.reportsworkshopgft.eventlog.domain.EventLogId;

public interface EventLogService {
    List<EventLog> findAllEventsLogs();

    EventLog findEventLogById(EventLogId id);

    void save(EventLog eventLog);

    List<OrderHistoryProjection> getOrderHistory();

    SystemStatsProjection getSystemStats();
}
