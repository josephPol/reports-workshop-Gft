package org.example.reportsworkshopgft.blockedorder.application.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.example.reportsworkshopgft.blockedorder.application.BlockedOrderService;
import org.example.reportsworkshopgft.blockedorder.application.exception.BlockedOrderNotFoundException;
import org.example.reportsworkshopgft.blockedorder.domain.BlockedOrder;
import org.example.reportsworkshopgft.blockedorder.infrastructure.BlockedOrderRepositoryJPA;
import org.example.reportsworkshopgft.blockedorder.infrastructure.persistence.BlockedOrderJPA;
import org.springframework.stereotype.Service;

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
        return found.map(this::toDomain).orElseThrow(() -> new BlockedOrderNotFoundException(id));
    }

    private BlockedOrder toDomain(BlockedOrderJPA jpa) {
        return new BlockedOrder(
                jpa.getOrderId(), jpa.getFactoryId(), jpa.getReason(), jpa.getBlockedSinceDay());
    }
}
