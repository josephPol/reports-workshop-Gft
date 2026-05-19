package org.example.reportsworkshopgft.blockedorder.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import org.example.reportsworkshopgft.blockedorder.application.BlockedOrderService;
import org.example.reportsworkshopgft.blockedorder.domain.BlockedOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

@ExtendWith(MockitoExtension.class)
class BlockedOrderControllerTest {

    @Mock private BlockedOrderService blockedOrderService;

    @InjectMocks private BlockedOrderController controller;

    @Test
    void should_return_all_blocked_orders_paginated() {
        List<BlockedOrder> mockOrders = List.of(new BlockedOrder("o1", "f1", "r1", 1));
        when(blockedOrderService.getAllBlockedOrders()).thenReturn(mockOrders);

        Page<BlockedOrder> result = controller.getAllBlockedOrders(0, 20);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getNumber()).isEqualTo(0);
    }

    @Test
    void should_return_empty_page_content_when_page_is_out_of_range() {
        List<BlockedOrder> mockOrders = List.of(new BlockedOrder("o1", "f1", "r1", 1));
        when(blockedOrderService.getAllBlockedOrders()).thenReturn(mockOrders);

        Page<BlockedOrder> result = controller.getAllBlockedOrders(10, 20);

        assertThat(result.getContent()).isEmpty();
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getNumber()).isEqualTo(10);
    }

    @Test
    void should_return_blocked_order_by_id() {
        BlockedOrder expected = new BlockedOrder("o2", "f2", "r2", 2);
        when(blockedOrderService.getBlockedOrderById("o2")).thenReturn(expected);

        BlockedOrder result = controller.getBlockedOrderById("o2");

        assertThat(result).isEqualTo(expected);
    }
}
