package org.example.reportsworskhopgft.eventlog.domain;

public enum EventType {
    // Time
    TIME_ADVANCED,

    // Transport
    TRUCK_REGISTERED,
    TRUCK_ASSIGNED,
    TRUCK_POSITION_UPDATED,
    DELIVERY_CREATED,
    DELIVERY_COMPLETED,

    // Production
    PRODUCTION_ORDER_CREATED,
    PRODUCTION_ORDER_STARTED,
    PRODUCTION_ORDER_BLOCKED,
    PRODUCTION_ORDER_COMPLETED,

    // Warehouse
    REPLENISHMENT_REQUESTED,
    WAREHOUSE_STOCK_CHANGED
}