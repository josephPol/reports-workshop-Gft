package org.example.reportsworskhopgft.blockedorder.infrastructure;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class BlockedOrderInfrastructureTest {

    @Test
    void shouldCreateControllerAndRepositoryAdapters() {
        assertNotNull(new BlockedOrderController());
    }
}
