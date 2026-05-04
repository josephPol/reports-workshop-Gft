package org.example.reportsworskhopgft.blockedorder.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BlockedOrderTest {

    @Test
    void shouldExposeStateThroughLombokGeneratedMethods() {
        BlockedOrder blockedOrder = new BlockedOrder("order-1", "factory-1", "reason", 3);

        assertEquals("order-1", blockedOrder.getOrderId());
        assertEquals("factory-1", blockedOrder.getFactoryId());
        assertEquals("reason", blockedOrder.getReason());
        assertEquals(3, blockedOrder.getBlockedSinceDay());
        assertTrue(blockedOrder.toString().contains("order-1"));
    }

    @Test
    void shouldCompareEntitiesByValue() {
        BlockedOrder left = new BlockedOrder("order-1", "factory-1", "reason", 3);
        BlockedOrder right = new BlockedOrder();
        right.setOrderId("order-1");
        right.setFactoryId("factory-1");
        right.setReason("reason");
        right.setBlockedSinceDay(3);

        assertEquals(left, right);
        assertEquals(left.hashCode(), right.hashCode());

        right.setReason("different-reason");

        assertNotEquals(left, right);
    }
}
