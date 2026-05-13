package org.example.reportsworskhopgft.eventlog.application.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.example.reportsworskhopgft.eventlog.application.EventLogService;
import org.example.reportsworskhopgft.eventlog.application.projections.OrderHistoryProjection;
import org.example.reportsworskhopgft.eventlog.application.projections.SystemStatsProjection;
import org.example.reportsworskhopgft.eventlog.domain.EventLog;
import org.example.reportsworskhopgft.eventlog.domain.EventLogId;
import org.example.reportsworskhopgft.eventlog.domain.EventType;
import org.example.reportsworskhopgft.eventlog.domain.SourceService;
import org.example.reportsworskhopgft.eventlog.infrastructure.EventLogRepositoryJPA;
import org.example.reportsworskhopgft.eventlog.infrastructure.persistence.EventLogIdJPA;
import org.example.reportsworskhopgft.eventlog.infrastructure.persistence.EventLogJPA;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventLogServiceImpl implements EventLogService {

    private final EventLogRepositoryJPA jpaRepository;
    private final ObjectMapper objectMapper;

    public EventLogServiceImpl(EventLogRepositoryJPA jpaRepository, ObjectMapper objectMapper) {
        this.jpaRepository = jpaRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<EventLog> findAllEventsLogs() {
        return jpaRepository.findAll().stream().map(this::mapToDomain).collect(Collectors.toList());
    }

    @Override
    public EventLog findEventLogById(EventLogId id) {
        EventLogIdJPA jpaId = new EventLogIdJPA(id.value());
        return jpaRepository.findById(jpaId).map(this::mapToDomain).orElse(null);
    }

    @Override
    @Transactional
    public void save(EventLog eventLog) {
        EventLogJPA jpaEntity = mapToJPA(eventLog);
        jpaRepository.save(jpaEntity);
    }

    @Override
    public List<OrderHistoryProjection> getOrderHistory() {
        return List.of();
    }

    @Override
    public SystemStatsProjection getSystemStats() {

        List<EventLogJPA> allEvents = jpaRepository.findAll();
        ;

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

        int trucksInTransit =
                (int)
                        truckStatuses.values().stream()
                                .filter(status -> status.equals("IN_TRANSIT"))
                                .count();

        return new SystemStatsProjection(
                totalOrders, completedOrders, blockedOrders, trucksInTransit);
    }

    @Transactional
    public void save(
            EventType eventType,
            SourceService sourceService,
            String payload,
            Integer simulationDay,
            String occurredAt) {
        save(
                EventLog.builder()
                        .id(EventLogId.generate())
                        .eventType(eventType)
                        .sourceService(sourceService)
                        .payload(payload)
                        .simulationDay(simulationDay)
                        .occurredAt(occurredAt)
                        .build());
    }

    private EventLog mapToDomain(EventLogJPA jpaEntity) {
        return new EventLog(
                new EventLogId(jpaEntity.getId().getValue()),
                jpaEntity.getEventType(),
                jpaEntity.getSourceService(),
                jpaEntity.getPayload(),
                jpaEntity.getSimulationDay(),
                jpaEntity.getOccurredAt());
    }

    private EventLogJPA mapToJPA(EventLog domain) {
        EventLogIdJPA jpaId = new EventLogIdJPA(domain.getId().value());
        return new EventLogJPA(
                jpaId,
                domain.getEventType(),
                domain.getSourceService(),
                domain.getPayload(),
                domain.getSimulationDay(),
                domain.getOccurredAt());
    }
}
