package org.example.reportsworkshopgft.blockedorder.infrastructure;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.example.reportsworkshopgft.blockedorder.application.BlockedOrderService;
import org.example.reportsworkshopgft.blockedorder.application.exception.BlockedOrderNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BlockedOrderController.class)
class BlockedOrderControllerWebMvcTest {

    @Autowired private MockMvc mockMvc;

    @MockitoBean private BlockedOrderService blockedOrderService;

    @Test
    void should_return_404_when_blocked_order_is_not_found() throws Exception {
        when(blockedOrderService.getBlockedOrderById("missing"))
                .thenThrow(new BlockedOrderNotFoundException("missing"));

        mockMvc.perform(get("/blockedOrders/missing")).andExpect(status().isNotFound());
    }
}
