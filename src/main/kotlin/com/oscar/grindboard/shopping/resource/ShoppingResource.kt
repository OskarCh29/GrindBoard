package com.oscar.grindboard.shopping.resource

import com.oscar.grindboard.common.model.ErrorResponse
import com.oscar.grindboard.shopping.dto.ShoppingEntryRequest
import com.oscar.grindboard.shopping.dto.ShoppingEntryResponse
import com.oscar.grindboard.shopping.dto.ShoppingItemRequest
import com.oscar.grindboard.shopping.service.ShoppingService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.time.LocalDate

@RestController
@RequestMapping("/api/shopping")
@Tag(name = "Shopping", description = "Daily shopping list with details")
internal class ShoppingResource(
    private val service: ShoppingService,
) {
    @Operation(
        summary = "Create a new shopping entry",
        description = "Create a new shopping entry for particular shopping day",
    )
    @ApiResponses(
        value =
            [
                ApiResponse(
                    responseCode = "201",
                    description = "The shopping entry created",
                    content =
                        [
                            Content(
                                mediaType = "application/json",
                                schema = Schema(implementation = ShoppingEntryResponse::class),
                            ),
                        ],
                ),
                ApiResponse(
                    responseCode = "400",
                    description = "Invalid shopping entry request",
                    content = [Content(schema = Schema(implementation = ErrorResponse::class))],
                ),
            ],
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun createShoppingEntry(
        @Valid @RequestBody shoppingEntry: ShoppingEntryRequest,
    ): Mono<ShoppingEntryResponse> = service.addShoppingEntry(shoppingEntry)

    @Operation(
        summary = "Add shopping item to particular shopping day",
        description = "Adds shopping item with details to shopping entry day",
    )
    @ApiResponses(
        value =
            [
                ApiResponse(
                    responseCode = "201",
                    description = "Item successfully added to shopping entry",
                    content =
                        [
                            Content(
                                mediaType = "application/json",
                                schema = Schema(implementation = ShoppingEntryResponse::class),
                            ),
                        ],
                ),
                ApiResponse(
                    responseCode = "400",
                    description = "Invalid shopping entry request",
                    content = [Content(schema = Schema(implementation = ErrorResponse::class))],
                ),
                ApiResponse(
                    responseCode = "404",
                    description = "Shopping entry not found",
                    content = [Content(schema = Schema(implementation = ErrorResponse::class))],
                ),
            ],
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{id}/items")
    fun addItemToShoppingEntry(
        @PathVariable id: String,
        @Valid @RequestBody item: ShoppingItemRequest,
    ): Mono<ShoppingEntryResponse> = service.addItem(id, item)

    @Operation(
        summary = "Delete shopping item from shopping entry",
        description =
            "Delete shopping item from shopping entry for particular day (deletes full item not just quantity)",
    )
    @ApiResponses(
        value =
            [
                ApiResponse(
                    responseCode = "204",
                    description = "Item successfully removed from shopping entry",
                ),
                ApiResponse(
                    responseCode = "404",
                    description = "Shopping entry not found",
                    content = [Content(schema = Schema(implementation = ErrorResponse::class))],
                ),
            ],
    )
    @DeleteMapping("/{id}/items/{itemId}")
    fun removeItemFromShoppingEntry(
        @PathVariable id: String,
        @PathVariable itemId: String,
    ): Mono<ShoppingEntryResponse> = service.deleteItem(id, itemId)

    @Operation(
        summary = "Update shopping item by item id",
        description = "Update shopping item by unique item id",
    )
    @ApiResponses(
        value =
            [
                ApiResponse(
                    responseCode = "200",
                    description = "Item successfully updated",
                    content =
                        [Content(schema = Schema(implementation = ShoppingEntryResponse::class))],
                ),
                ApiResponse(
                    responseCode = "400",
                    description = "Invalid shopping entry request",
                    content = [Content(schema = Schema(implementation = ErrorResponse::class))],
                ),
                ApiResponse(
                    responseCode = "404",
                    description = "Shopping entry not found",
                    content = [Content(schema = Schema(implementation = ErrorResponse::class))],
                ),
            ],
    )
    @PutMapping("/{id}/items")
    fun updateItem(
        @PathVariable id: String,
        @Valid @RequestBody item: ShoppingItemRequest,
    ): Mono<ShoppingEntryResponse> = service.updateItem(id, item)

    @Operation(
        summary = "Get full shopping entry for particular date provided",
        description =
            "Returns full shopping day response with total value calculation for particular day",
    )
    @ApiResponses(
        value =
            [
                ApiResponse(
                    responseCode = "200",
                    description = "Shopping entry successfully obtained",
                ),
                ApiResponse(
                    responseCode = "404",
                    description = "Shopping entry not found",
                    content = [Content(schema = Schema(implementation = ErrorResponse::class))],
                ),
            ],
    )
    @GetMapping
    fun getShoppingEntry(
        @RequestParam date: LocalDate,
    ): Mono<ShoppingEntryResponse> = service.getByDate(date)

    @Operation(
        summary = "Deletes shopping entry",
        description = "Delete shopping entry by provided id with all it's shopping items",
    )
    @ApiResponses(
        value =
            [
                ApiResponse(
                    responseCode = "204",
                    description = "Shopping entry successfully removed",
                ),
            ],
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    fun deleteShoppingEntry(
        @PathVariable id: String,
    ): Mono<Void> = service.delete(id)
}
