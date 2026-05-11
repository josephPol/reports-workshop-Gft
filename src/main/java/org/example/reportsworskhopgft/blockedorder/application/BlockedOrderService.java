package org.example.reportsworskhopgft.blockedorder.application;

import org.example.reportsworskhopgft.blockedorder.domain.BlockedOrder;
import org.springframework.cglib.core.Block;

import java.util.List;

public interface BlockedOrderService {

    List<BlockedOrder> getAllBlockedOrders();
    BlockedOrder getBlockedOrderById(String id);
}
