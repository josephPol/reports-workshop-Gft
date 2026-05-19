package org.example.reportsworkshopgft.eventlog.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.reportsworkshopgft.eventlog.application.EventLogService;
import org.example.reportsworkshopgft.eventlog.application.projections.OrderHistoryProjection;
import org.example.reportsworkshopgft.eventlog.application.projections.SystemStatsProjection;
import org.example.reportsworkshopgft.eventlog.domain.EventLog;
import org.example.reportsworkshopgft.eventlog.domain.EventLogId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
@Tag(
        name = "Dashboard and Reports",
        description = "Endpoints for querying factory and transport status")
public class EventLogController {

    private final EventLogService eventLogService;

    @GetMapping({"/", ""})
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get all event logs",
            description = "Returns the complete event sourcing log of all system activities.")
    public List<EventLog> getAllEventLogs() {
        List<EventLog> eventLogJPAList = eventLogService.findAllEventsLogs();
        log.info("Events found: {}", eventLogJPAList.size());
        return eventLogJPAList;
    }

    @GetMapping("/{reports_id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Find event by ID",
            description = "Returns the details of a specific system event.")
    public EventLog getEventLogById(@PathVariable("reports_id") EventLogId id) {
        return eventLogService.findEventLogById(id);
    }

    @GetMapping("/stats")
    @Operation(
            summary = "Get global statistics",
            description =
                    "Calculates real-time totals of processed, completed, and blocked orders, as well as trucks in transit.")
    public ResponseEntity<SystemStatsProjection> getSystemStats() {
        SystemStatsProjection stats = eventLogService.getSystemStats();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/orders/history")
    @Operation(
            summary = "Get order history",
            description =
                    "Returns the most recent status of each production order, unifying events by ID.")
    public ResponseEntity<List<OrderHistoryProjection>> getOrderHistory() {
        List<OrderHistoryProjection> history = eventLogService.getOrderHistory();
        return ResponseEntity.ok(history);
    }
}
