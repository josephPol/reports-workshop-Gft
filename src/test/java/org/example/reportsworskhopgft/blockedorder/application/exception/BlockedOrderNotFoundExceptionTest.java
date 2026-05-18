package org.example.reportsworskhopgft.blockedorder.application.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class BlockedOrderNotFoundExceptionTest {

    @Test
    void should_expose_id() {
        BlockedOrderNotFoundException ex = new BlockedOrderNotFoundException("order-1");

        assertThat(ex.getId()).isEqualTo("order-1");
        assertThat(ex.getMessage()).contains("order-1");
    }
}
