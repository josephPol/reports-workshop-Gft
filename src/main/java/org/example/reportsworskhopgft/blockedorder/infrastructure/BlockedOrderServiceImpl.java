package org.example.reportsworskhopgft.blockedorder.infrastructure;

import org.example.reportsworskhopgft.blockedorder.application.BlockedOrderService;
import org.example.reportsworskhopgft.blockedorder.domain.BlockedOrder;
import org.example.reportsworskhopgft.blockedorder.infrastructure.persistence.BlockedOrderJPA;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BlockedOrderServiceImpl implements BlockedOrderService {

    private final BlockedOrderRepositoryJPA blockedOrderRepositoryJPA;

    public BlockedOrderServiceImpl(BlockedOrderRepositoryJPA blockedOrderRepositoryJPA) {
        this.blockedOrderRepositoryJPA = blockedOrderRepositoryJPA;
    }

    @Override
    public List<BlockedOrder> getAllBlockedOrders() {
        return blockedOrderRepositoryJPA.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public BlockedOrder getBlockedOrderById(String id) {
        Optional<BlockedOrderJPA> found = blockedOrderRepositoryJPA.findById(id);
        return found.map(this::toDomain).orElse(null);
    }

    private BlockedOrder toDomain(BlockedOrderJPA jpa) {
        return new BlockedOrder(
                jpa.getOrderId(),
                jpa.getFactoryId(),
                jpa.getReason(),
                jpa.getBlockedSinceDay()
        );
    }
}

