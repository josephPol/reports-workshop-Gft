package org.example.reportsworskhopgft.eventlog.infrastructure;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventLogController.class)
class EventLogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_load_controller_context() {
     try {
            mockMvc.perform(get("/any-endpoint"))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            throw new RuntimeException("Error al probar el contexto del controlador", e);
        }
    }
}