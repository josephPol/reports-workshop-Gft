package org.example.reportsworskhopgft.eventlog.infrastructure;

import lombok.RequiredArgsConstructor;
import org.example.reportsworskhopgft.eventlog.application.EventLogRepository;
import org.example.reportsworskhopgft.eventlog.domain.EventLog;
import org.example.reportsworskhopgft.eventlog.infrastructure.persistence.EventLogJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class EventLogController {

    private final EventLogRepository eventLogRepository;

    @GetMapping({"/",""})
    public List<EventLogJPA> getAllEventLogs() {
        return eventLogRepository.findAllEventsLogs();
    }
}
