package org.example.reportsworskhopgft.blockedorder.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.example.reportsworskhopgft.blockedorder.application.exception.BlockedOrderNotFoundException;
import org.example.reportsworskhopgft.blockedorder.application.impl.BlockedOrderServiceImpl;
import org.example.reportsworskhopgft.blockedorder.domain.BlockedOrder;
import org.example.reportsworskhopgft.blockedorder.infrastructure.persistence.BlockedOrderJPA;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BlockedOrderServiceImplTest {

    @Mock private BlockedOrderRepositoryJPA blockedOrderRepositoryJPA;

    @InjectMocks private BlockedOrderServiceImpl service;

    @Test
    void should_map_all_entities_to_domain_when_getAllBlockedOrders_is_called() {
        BlockedOrderJPA entity = new BlockedOrderJPA("order-1", "factory-1", "reason-1", 10);
        when(blockedOrderRepositoryJPA.findAll()).thenReturn(List.of(entity));

        List<BlockedOrder> result = service.getAllBlockedOrders();

        assertThat(result).hasSize(1);
        BlockedOrder mapped = result.get(0);
        assertThat(mapped.getOrderId()).isEqualTo("order-1");
        assertThat(mapped.getFactoryId()).isEqualTo("factory-1");
        assertThat(mapped.getReason()).isEqualTo("reason-1");
        assertThat(mapped.getBlockedSinceDay()).isEqualTo(10);
    }

    @Test
    void should_throw_when_getBlockedOrderById_is_called_and_not_found() {
        when(blockedOrderRepositoryJPA.findById("missing")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getBlockedOrderById("missing"))
                .isInstanceOf(BlockedOrderNotFoundException.class)
                .hasMessageContaining("missing");
    }

    @Test
    void should_return_mapped_entity_when_getBlockedOrderById_is_called_and_found() {
        BlockedOrderJPA entity = new BlockedOrderJPA("order-2", "factory-2", "reason-2", 3);
        when(blockedOrderRepositoryJPA.findById("order-2")).thenReturn(Optional.of(entity));

        BlockedOrder result = service.getBlockedOrderById("order-2");

        assertThat(result).isNotNull();
        assertThat(result.getOrderId()).isEqualTo("order-2");
        assertThat(result.getFactoryId()).isEqualTo("factory-2");
        assertThat(result.getReason()).isEqualTo("reason-2");
        assertThat(result.getBlockedSinceDay()).isEqualTo(3);
    }
}
