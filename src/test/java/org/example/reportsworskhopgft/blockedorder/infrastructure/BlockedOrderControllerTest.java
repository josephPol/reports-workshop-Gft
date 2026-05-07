package org.example.reportsworskhopgft.blockedorder.infrastructure;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class BlockedOrderControllerTest {

    @Test
    void should_instantiate_controller() {
        BlockedOrderController controller = new BlockedOrderController();
        assertNotNull(controller);
    }
}

