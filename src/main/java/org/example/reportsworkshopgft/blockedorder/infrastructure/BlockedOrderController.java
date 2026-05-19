package org.example.reportsworkshopgft.blockedorder.infrastructure;

import java.util.List;
import org.example.reportsworkshopgft.blockedorder.application.BlockedOrderService;
import org.example.reportsworkshopgft.blockedorder.domain.BlockedOrder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("blockedOrders")
public class BlockedOrderController {
    private final BlockedOrderService blockedOrderService;

    public BlockedOrderController(BlockedOrderService blockedOrderService) {
        this.blockedOrderService = blockedOrderService;
    }

    @GetMapping({"/", ""})
    @ResponseStatus(HttpStatus.OK)
    public List<BlockedOrder> getAllBlockedOrders() {
        return blockedOrderService.getAllBlockedOrders();
    }

    @GetMapping("/{BlockedOrder_id}")
    @ResponseStatus(HttpStatus.OK)
    public BlockedOrder getBlockedOrderById(
            @PathVariable("BlockedOrder_id") String blockedOrderId) {
        return blockedOrderService.getBlockedOrderById(blockedOrderId);
    }
}
