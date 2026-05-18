package org.example.reportsworskhopgft.blockedorder.application.exception;

public class BlockedOrderNotFoundException extends RuntimeException {

    private final String id;

    public BlockedOrderNotFoundException(String id) {
        super("BlockedOrder not found: " + id);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
