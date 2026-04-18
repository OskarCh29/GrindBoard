package com.oscar.grindboard.shopping.api

import com.oscar.grindboard.shopping.model.ShoppingEntry
import com.oscar.grindboard.shopping.model.ShoppingItem
import com.oscar.grindboard.shopping.service.ShoppingEntryService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.time.LocalDate

@RestController
@RequestMapping("/api/shopping")
class ShoppingResource(
    private val service: ShoppingEntryService,
) {
    @PostMapping
    fun createShoppingEntry(
        @RequestBody shoppingEntry: ShoppingEntry,
    ): Mono<ShoppingEntry> = service.addShoppingEntry(shoppingEntry)

    @PostMapping("/{id}/items")
    fun addItemToShoppingEntry(
        @PathVariable id: String,
        @RequestBody item: ShoppingItem,
    ): Mono<ShoppingEntry> = service.addItem(id, item)

    @DeleteMapping("/{id}/items/{itemId}")
    fun removeItemFromShoppingEntry(
        @PathVariable id: String,
        @PathVariable itemId: String,
    ): Mono<ShoppingEntry> = service.deleteItem(id, itemId)

    @PutMapping("/{id}/items")
    fun updateItem(@PathVariable id: String, @RequestBody item: ShoppingItem): Mono<ShoppingEntry> =
        service.updateItem(id, item)

    @GetMapping
    fun getShoppingEntry(
        @RequestParam date: LocalDate,
    ): Mono<ShoppingEntry> = service.getByDate(date)

    @DeleteMapping("/{id}")
    fun deleteShoppingEntry(
        @PathVariable id: String,
    ): Mono<Void> = service.delete(id)
}
