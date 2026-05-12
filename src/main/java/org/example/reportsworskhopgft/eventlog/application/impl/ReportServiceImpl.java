package org.example.reportsworskhopgft.eventlog.application.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.reportsworskhopgft.eventlog.application.ReportService;
import org.example.reportsworskhopgft.eventlog.application.projections.OrderHistoryProjection;
import org.example.reportsworskhopgft.eventlog.application.projections.SystemStatsProjection;
import org.example.reportsworskhopgft.eventlog.domain.EventLog;
import org.example.reportsworskhopgft.eventlog.domain.EventType;
import org.example.reportsworskhopgft.eventlog.infrastructure.EventLogRepositoryJPA;
import org.example.reportsworskhopgft.eventlog.infrastructure.persistence.EventLogJPA;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final EventLogRepositoryJPA eventLogRepository;
    private final ObjectMapper objectMapper;

    @Override
    public SystemStatsProjection getSystemStats() {

        List<EventLogJPA> allEvents = eventLogRepository.findAll();

        int totalOrders = 0;
        int completedOrders = 0;
        int blockedOrders = 0;


        Map<String, String> truckStatuses = new HashMap<>();

        for (EventLogJPA log : allEvents) {
            if (log.getEventType() == EventType.PRODUCTION_ORDER_CREATED) totalOrders++;
            if (log.getEventType() == EventType.PRODUCTION_ORDER_COMPLETED) completedOrders++;
            if (log.getEventType() == EventType.PRODUCTION_ORDER_BLOCKED) blockedOrders++;

            if (log.getEventType() == EventType.TRUCK_STATUS_CHANGED) {
                try {

                    JsonNode payload = objectMapper.readTree(log.getPayload());
                    String truckId = payload.get("truckId").asText();
                    String status = payload.get("status").asText();
                    truckStatuses.put(truckId, status);
                } catch (Exception e) {

                    System.err.println("Error parsing truck status event: " + e.getMessage());
                }
            }
        }


        int trucksInTransit = (int) truckStatuses.values().stream()
                .filter(status -> status.equals("IN_TRANSIT"))
                .count();

        return new SystemStatsProjection(totalOrders, completedOrders, blockedOrders, trucksInTransit);
    }

    @Override
    public List<OrderHistoryProjection> getOrderHistory() {

        return new ArrayList<>();
    }
}