package org.example.reportsworskhopgft.eventlog.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.reportsworskhopgft.eventlog.application.EventLogService;
import org.example.reportsworskhopgft.eventlog.application.projections.OrderHistoryProjection;
import org.example.reportsworskhopgft.eventlog.application.projections.SystemStatsProjection;
import org.example.reportsworskhopgft.eventlog.domain.EventLog;
import org.example.reportsworskhopgft.eventlog.domain.EventLogId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class EventLogController {

    private final EventLogService eventLogService;

    @GetMapping({"/",""})
    @ResponseStatus(HttpStatus.OK)
    public List<EventLog> getAllEventLogs() {
        List<EventLog> eventLogJPAList = eventLogService.findAllEventsLogs();
        log.info("Events found: " + eventLogJPAList.size());
        return eventLogJPAList;
    }

    @GetMapping("/{reports_id}")
    @ResponseStatus(HttpStatus.OK)
    public EventLog getEventLogById(@PathVariable("reports_id") EventLogId id) {
        return eventLogService.findEventLogById(id);
    }

    @GetMapping("/stats")
    public ResponseEntity<SystemStatsProjection> getSystemStats() {
        SystemStatsProjection stats = eventLogService.getSystemStats();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/orders/history")
    public ResponseEntity<List<OrderHistoryProjection>> getOrderHistory() {
        List<OrderHistoryProjection> history = eventLogService.getOrderHistory();
        return ResponseEntity.ok(history);
    }
}
