package org.example.reportsworskhopgft.eventlog.infrastructure.messaging.warehouse;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public record WarehouseOrderBlockedEvent(@JsonProperty("orderId") UUID orderId) {}
