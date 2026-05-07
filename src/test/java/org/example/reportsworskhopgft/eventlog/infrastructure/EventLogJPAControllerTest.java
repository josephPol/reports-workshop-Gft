package org.example.reportsworskhopgft.eventlog.infrastructure;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EventLogJPAControllerTest {

    @Test
    void shouldInstantiateEmptyController() {

        EventLogController controller = new EventLogController();
        assertNotNull(controller, "El controlador debería poder instanciarse");
    }
}