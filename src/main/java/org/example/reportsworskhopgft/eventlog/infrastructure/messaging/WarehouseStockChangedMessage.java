package org.example.reportsworskhopgft.eventlog.infrastructure.messaging;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WarehouseStockChangedMessage(
        @JsonProperty("productId") String productId,
        @JsonProperty("quantity") int quantity,
        @JsonProperty("type") String type
) {
    public String toPayload() {
        return "{\"productId\":\"" + productId + "\",\"quantity\":" + quantity + ",\"type\":\"" + type + "\"}";
    }
}