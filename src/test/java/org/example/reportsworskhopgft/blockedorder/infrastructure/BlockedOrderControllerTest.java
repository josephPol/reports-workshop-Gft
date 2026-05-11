package org.example.reportsworskhopgft.blockedorder.infrastructure;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class BlockedOrderControllerTest {

    @Test
    void shouldCreateController() {
        assertNotNull(new BlockedOrderController());
    }
}
