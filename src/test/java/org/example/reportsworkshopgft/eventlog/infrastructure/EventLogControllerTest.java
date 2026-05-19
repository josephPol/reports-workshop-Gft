package org.example.reportsworkshopgft.eventlog.infrastructure;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.example.reportsworkshopgft.eventlog.application.EventLogService;
import org.example.reportsworkshopgft.eventlog.application.exception.EventLogNotFoundException;
import org.example.reportsworkshopgft.eventlog.application.projections.OrderHistoryProjection;
import org.example.reportsworkshopgft.eventlog.application.projections.SystemStatsProjection;
import org.example.reportsworkshopgft.eventlog.domain.EventLog;
import org.example.reportsworkshopgft.eventlog.domain.EventLogId;
import org.example.reportsworkshopgft.eventlog.domain.EventType;
import org.example.reportsworkshopgft.eventlog.domain.SourceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(EventLogController.class)
class EventLogControllerTest {

    @Autowired private MockMvc mockMvc;

    @Autowired private ObjectMapper objectMapper;

    @MockitoBean private EventLogService eventLogService;

    @Test
    void should_return_all_events() throws Exception {
        EventLog event =
                EventLog.builder()
                        .id(EventLogId.generate())
                        .eventType(EventType.TRUCK_REGISTERED)
                        .sourceService(SourceService.TRANSPORT)
                        .payload("{\"ok\":true}")
                        .simulationDay(1)
                        .occurredAt("2026-05-11T11:00:00")
                        .build();

        when(eventLogService.findAllEventsLogs()).thenReturn(List.of(event));

        mockMvc.perform(get("/reports/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].eventType").value("TRUCK_REGISTERED"))
                .andExpect(jsonPath("$[0].sourceService").value("TRANSPORT"))
                .andExpect(jsonPath("$[0].simulationDay").value(1));
    }

    @Test
    void should_return_event_by_id() throws Exception {
        EventLogId id = EventLogId.generate();
        EventLog expected =
                EventLog.builder()
                        .id(id)
                        .eventType(EventType.TRUCK_REGISTERED)
                        .sourceService(SourceService.TRANSPORT)
                        .payload("{\"ok\":true}")
                        .simulationDay(1)
                        .occurredAt("2026-05-11T11:00:00")
                        .build();

        // Cambiamos el id fijo por un "cualquier cosa que sea de la clase EventLogId"
        when(eventLogService.findEventLogById(org.mockito.ArgumentMatchers.any()))
                .thenReturn(expected);

        mockMvc.perform(get("/reports/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.eventType").value("TRUCK_REGISTERED"))
                .andExpect(jsonPath("$.sourceService").value("TRANSPORT"))
                .andExpect(jsonPath("$.simulationDay").value(1));
    }

    @Test
    void should_return_404_when_event_is_not_found() throws Exception {
        EventLogId id = EventLogId.generate();
        when(eventLogService.findEventLogById(org.mockito.ArgumentMatchers.any()))
                .thenThrow(new EventLogNotFoundException(id));

        mockMvc.perform(get("/reports/" + id)).andExpect(status().isNotFound());
    }

    @Test
    void should_return_system_stats() throws Exception {
        SystemStatsProjection mockStats = new SystemStatsProjection(10, 5, 1, 3);
        when(eventLogService.getSystemStats()).thenReturn(mockStats);

        mockMvc.perform(get("/reports/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalOrders").value(10))
                .andExpect(jsonPath("$.completedOrders").value(5))
                .andExpect(jsonPath("$.blockedOrders").value(1))
                .andExpect(jsonPath("$.trucksInTransit").value(3));
    }

    @Test
    void should_return_order_history() throws Exception {
        OrderHistoryProjection history =
                new OrderHistoryProjection("ORD-001", "FAC-001", "COMPLETED", 2);
        when(eventLogService.getOrderHistory()).thenReturn(List.of(history));

        mockMvc.perform(get("/reports/orders/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].orderId").value("ORD-001"))
                .andExpect(jsonPath("$[0].factoryId").value("FAC-001"))
                .andExpect(jsonPath("$[0].status").value("COMPLETED"))
                .andExpect(jsonPath("$[0].simulationDay").value(2));
    }
}
