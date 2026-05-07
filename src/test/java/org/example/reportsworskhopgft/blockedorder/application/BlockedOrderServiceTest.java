package org.example.reportsworskhopgft.blockedorder.application;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class BlockedOrderServiceTest {

    @Test
    void should_instantiate_service() {
        BlockedOrderService service = new BlockedOrderService();
        assertNotNull(service);
    }
}

