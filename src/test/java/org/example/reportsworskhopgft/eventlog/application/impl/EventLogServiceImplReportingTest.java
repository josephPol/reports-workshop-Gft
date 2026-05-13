package org.example.reportsworskhopgft.eventlog.application.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.example.reportsworskhopgft.eventlog.application.projections.OrderHistoryProjection;
import org.example.reportsworskhopgft.eventlog.application.projections.SystemStatsProjection;
import org.example.reportsworskhopgft.eventlog.domain.EventType;
import org.example.reportsworskhopgft.eventlog.infrastructure.EventLogRepositoryJPA;
import org.example.reportsworskhopgft.eventlog.infrastructure.persistence.EventLogJPA;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EventLogServiceImplTest {

    @Mock private EventLogRepositoryJPA eventLogRepository;

    @Mock private ObjectMapper objectMapper;

    @InjectMocks private EventLogServiceImpl eventLogService;

    @Test
    void should_calculate_system_stats_correctly() throws Exception {
        // Arrange
        EventLogJPA orderCreated = new EventLogJPA();
        orderCreated.setEventType(EventType.PRODUCTION_ORDER_CREATED);

        EventLogJPA truckStatus = new EventLogJPA();
        truckStatus.setEventType(EventType.TRUCK_STATUS_CHANGED);
        truckStatus.setPayload("{\"truckId\":\"T1\", \"status\":\"IN_TRANSIT\"}");

        EventLogJPA orderCompleted = new EventLogJPA();
        orderCompleted.setEventType(EventType.PRODUCTION_ORDER_COMPLETED);

        EventLogJPA orderBlocked = new EventLogJPA();
        orderBlocked.setEventType(EventType.PRODUCTION_ORDER_BLOCKED);

        EventLogJPA corruptTruck = new EventLogJPA();
        corruptTruck.setEventType(EventType.TRUCK_STATUS_CHANGED);
        corruptTruck.setPayload("not-valid-json");

        // Mock repository to return all events
        when(eventLogRepository.findAll())
                .thenReturn(
                        List.of(
                                orderCreated,
                                orderCompleted,
                                orderBlocked,
                                truckStatus,
                                corruptTruck));

        // Forced error in Jackson
        when(objectMapper.readTree("not-valid-json"))
                .thenThrow(new RuntimeException("Parse error"));

        JsonNode mockNode =
                new ObjectMapper().readTree("{\"truckId\":\"T1\", \"status\":\"IN_TRANSIT\"}");
        when(objectMapper.readTree(truckStatus.getPayload())).thenReturn(mockNode);

        // Act
        SystemStatsProjection stats = eventLogService.getSystemStats();

        // Assert
        assertEquals(1, stats.totalOrders());
        assertEquals(1, stats.completedOrders());
        assertEquals(1, stats.blockedOrders());
        assertEquals(1, stats.trucksInTransit());
    }

    @Test
    void should_return_empty_order_history_initially() {
        // Act
        List<OrderHistoryProjection> history = eventLogService.getOrderHistory();

        // Assert
        org.junit.jupiter.api.Assertions.assertTrue(
                history.isEmpty(), "Order history list should be empty");
    }
}
