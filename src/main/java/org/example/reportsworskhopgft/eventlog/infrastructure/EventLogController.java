package org.example.reportsworskhopgft.eventlog.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.reportsworskhopgft.eventlog.application.EventLogRepository;
import org.example.reportsworskhopgft.eventlog.domain.EventLogId;
import org.example.reportsworskhopgft.eventlog.infrastructure.persistence.EventLogJPA;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class EventLogController {

    private final EventLogRepository eventLogRepository;

    @GetMapping({"/",""})
    @ResponseStatus(HttpStatus.OK)
    public List<EventLogJPA> getAllEventLogs() {
        List<EventLogJPA> eventLogJPAList = eventLogRepository.findAllEventsLogs();
        log.info("Events found: " + eventLogJPAList.size());
        return eventLogJPAList;
    }

    @GetMapping("/{reports_id}")
    @ResponseStatus(HttpStatus.OK)
    public EventLogJPA getEventLogById(@PathVariable("reports_id") EventLogId id) {
        return eventLogRepository.findEventLogById(id);
    }
}
