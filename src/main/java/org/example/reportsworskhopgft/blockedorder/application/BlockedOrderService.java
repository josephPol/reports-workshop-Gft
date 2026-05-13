package org.example.reportsworskhopgft.blockedorder.application;

import java.util.List;
import org.example.reportsworskhopgft.blockedorder.domain.BlockedOrder;

public interface BlockedOrderService {

    List<BlockedOrder> getAllBlockedOrders();

    BlockedOrder getBlockedOrderById(String id);
}
