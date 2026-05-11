package org.example.reportsworskhopgft.blockedorder.infrastructure;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class BlockedOrderControllerTest {

    @Test
    void should_instantiate_blocked_order_controller() {
        assertNotNull(new BlockedOrderController());
    }
}