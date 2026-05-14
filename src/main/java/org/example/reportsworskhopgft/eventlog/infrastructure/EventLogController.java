package org.example.reportsworskhopgft.eventlog.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Dashboard and Reports", description = "Endpoints for retrieving system reports and statistics based on the event log.")
public class EventLogController {

    private final EventLogService eventLogService;

    @GetMapping({"/",""})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "List every event", description = "Returns the complete log of everything that is happening in the system.")
    public List<EventLog> getAllEventLogs() {
        List<EventLog> eventLogJPAList = eventLogService.findAllEventsLogs();
        log.info("Events found: " + eventLogJPAList.size());
        return eventLogJPAList;
    }

    @GetMapping("/{reports_id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find event for ID", description = "Returns the details of a specific event.")
    public EventLog getEventLogById(@PathVariable("reports_id") EventLogId id) {
        return eventLogService.findEventLogById(id);
    }

    @GetMapping("/stats")
    @Operation(summary = "Obtain global statistics", description = "Calculate in real time the total amount of orders registered, completed, blocked and trucks in transit.")
    public ResponseEntity<SystemStatsProjection> getSystemStats() {
        SystemStatsProjection stats = eventLogService.getSystemStats();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/orders/history")
    @Operation(summary = "Orders' history", description = "Returns the last state of every order in production by ID.")
    public ResponseEntity<List<OrderHistoryProjection>> getOrderHistory() {
        List<OrderHistoryProjection> history = eventLogService.getOrderHistory();
        return ResponseEntity.ok(history);
    }
}