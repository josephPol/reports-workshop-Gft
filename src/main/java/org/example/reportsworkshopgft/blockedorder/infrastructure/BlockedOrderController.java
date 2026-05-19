package org.example.reportsworkshopgft.blockedorder.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.reportsworkshopgft.blockedorder.application.BlockedOrderService;
import org.example.reportsworkshopgft.blockedorder.domain.BlockedOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("blockedOrders")
@RequiredArgsConstructor
@Tag(name = "Blocked Orders", description = "APIs for managing blocked production orders")
public class BlockedOrderController {
    private final BlockedOrderService blockedOrderService;

    @GetMapping({"/", ""})
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "List all blocked orders (paginated)",
            description =
                    "Retrieves a paginated list of all production orders that are currently blocked, "
                            + "including the blocking reason, affected factory, and blocking duration. "
                            + "Supports custom pagination parameters.")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successfully retrieved paginated blocked orders",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = BlockedOrder.class))),
                @ApiResponse(responseCode = "500", description = "Internal server error occurred")
            })
    public Page<BlockedOrder> getAllBlockedOrders(
            @Parameter(description = "Page number (0-indexed)", example = "0")
                    @RequestParam(defaultValue = "0")
                    int page,
            @Parameter(description = "Page size (number of records per page)", example = "20")
                    @RequestParam(defaultValue = "20")
                    int size) {
        log.info("Fetching blocked orders - page: {}, size: {}", page, size);
        java.util.List<BlockedOrder> allOrders = blockedOrderService.getAllBlockedOrders();
        int start = page * size;
        int end = Math.min(start + size, allOrders.size());
        java.util.List<BlockedOrder> pageContent =
                start >= allOrders.size() ? java.util.List.of() : allOrders.subList(start, end);
        log.info(
                "Blocked orders retrieved: {} total, {} on this page",
                allOrders.size(),
                pageContent.size());
        return new PageImpl<BlockedOrder>(
                pageContent, PageRequest.of(page, size), allOrders.size());
    }

    @GetMapping("/{BlockedOrder_id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get blocked order by ID",
            description =
                    "Retrieves detailed information about a specific blocked order by its unique identifier")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successfully retrieved the blocked order",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = BlockedOrder.class))),
                @ApiResponse(
                        responseCode = "404",
                        description = "Blocked order not found with the given ID"),
                @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    public BlockedOrder getBlockedOrderById(
            @PathVariable("BlockedOrder_id")
                    @Parameter(
                            name = "BlockedOrder_id",
                            description = "Unique identifier of the blocked order",
                            example = "ORD-9982",
                            required = true)
                    String blockedOrderId) {
        log.info("Fetching blocked order with ID: {}", blockedOrderId);
        BlockedOrder blockedOrder = blockedOrderService.getBlockedOrderById(blockedOrderId);
        log.info("Successfully retrieved blocked order: {}", blockedOrderId);
        return blockedOrder;
    }
}
