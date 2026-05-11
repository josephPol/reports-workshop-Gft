package org.example.reportsworskhopgft.blockedorder.infrastructure;

import org.example.reportsworskhopgft.blockedorder.application.BlockedOrderService;
import org.example.reportsworskhopgft.blockedorder.domain.BlockedOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BlockedOrderControllerTest {

    @Mock
    private BlockedOrderService blockedOrderService;

    @InjectMocks
    private BlockedOrderController controller;

    @Test
    void should_return_all_blocked_orders() {
        List<BlockedOrder> expected = List.of(new BlockedOrder("o1", "f1", "r1", 1));
        when(blockedOrderService.getAllBlockedOrders()).thenReturn(expected);

        List<BlockedOrder> result = controller.getAllBlockedOrders();

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void should_return_blocked_order_by_id() {
        BlockedOrder expected = new BlockedOrder("o2", "f2", "r2", 2);
        when(blockedOrderService.getBlockedOrderById("o2")).thenReturn(expected);

        BlockedOrder result = controller.getBlockedOrderById("o2");

        assertThat(result).isEqualTo(expected);
    }
}

