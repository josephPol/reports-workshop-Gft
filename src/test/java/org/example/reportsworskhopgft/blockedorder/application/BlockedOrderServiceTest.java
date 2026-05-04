package org.example.reportsworskhopgft.blockedorder.application;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class BlockedOrderServiceTest {

    @Test
    void shouldCreateApplicationServiceInstance() {
        assertNotNull(new BlockedOrderService());
    }
}
