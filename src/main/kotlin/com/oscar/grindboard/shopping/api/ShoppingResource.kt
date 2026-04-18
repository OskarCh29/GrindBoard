package com.oscar.grindboard.shopping.api

import com.oscar.grindboard.shopping.dto.ShoppingEntryRequest
import com.oscar.grindboard.shopping.dto.ShoppingEntryResponse
import com.oscar.grindboard.shopping.dto.ShoppingItemRequest
import com.oscar.grindboard.shopping.service.ShoppingEntryService
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
class ShoppingResource(
    private val service: ShoppingEntryService,
) {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun createShoppingEntry(
        @Valid @RequestBody shoppingEntry: ShoppingEntryRequest,
    ): Mono<ShoppingEntryResponse> = service.addShoppingEntry(shoppingEntry)

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{id}/items")
    fun addItemToShoppingEntry(
        @PathVariable id: String,
        @Valid @RequestBody item: ShoppingItemRequest,
    ): Mono<ShoppingEntryResponse> = service.addItem(id, item)

    @DeleteMapping("/{id}/items/{itemId}")
    fun removeItemFromShoppingEntry(
        @PathVariable id: String,
        @PathVariable itemId: String,
    ): Mono<ShoppingEntryResponse> = service.deleteItem(id, itemId)

    @PutMapping("/{id}/items")
    fun updateItem(
        @PathVariable id: String,
        @Valid @RequestBody item: ShoppingItemRequest,
    ): Mono<ShoppingEntryResponse> = service.updateItem(id, item)

    @GetMapping
    fun getShoppingEntry(
        @RequestParam date: LocalDate,
    ): Mono<ShoppingEntryResponse> = service.getByDate(date)

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    fun deleteShoppingEntry(
        @PathVariable id: String,
    ): Mono<Void> = service.delete(id)
}
