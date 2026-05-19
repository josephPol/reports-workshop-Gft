package org.example.reportsworkshopgft.blockedorder.application;

import java.util.List;
import org.example.reportsworkshopgft.blockedorder.domain.BlockedOrder;

public interface BlockedOrderService {

    List<BlockedOrder> getAllBlockedOrders();

    BlockedOrder getBlockedOrderById(String id);
}
