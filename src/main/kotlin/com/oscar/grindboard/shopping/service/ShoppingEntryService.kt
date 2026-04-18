package com.oscar.grindboard.shopping.service

import com.oscar.grindboard.shopping.model.ShoppingEntry
import com.oscar.grindboard.shopping.model.ShoppingItem
import com.oscar.grindboard.shopping.repository.ShoppingEntryRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.LocalDate

@Service
class ShoppingEntryService(
    private val shoppingRepository: ShoppingEntryRepository,
) {
    fun addShoppingEntry(entry: ShoppingEntry): Mono<ShoppingEntry> =
        shoppingRepository.save(entry).onErrorMap {
            IllegalStateException("Entry for this date already exists")
        }

    fun getByDate(date: LocalDate): Mono<ShoppingEntry> = shoppingRepository.findByDate(date)

    fun delete(id: String): Mono<Void> = shoppingRepository.deleteById(id)

    fun addItem(
        id: String,
        newItem: ShoppingItem,
    ): Mono<ShoppingEntry> =
        shoppingRepository
            .findById(id)
            .switchIfEmpty(Mono.error(NoSuchElementException("No shopping entry found")))
            .map { entry ->
                val existingItem = entry.items.find { it.name.equals(newItem.name, ignoreCase = true) }

                val updatedItems =
                    if (existingItem != null) {
                        entry.items.map {
                            if (it.id == existingItem.id) {
                                it.copy(quantity = it.quantity + newItem.quantity)
                            } else {
                                it
                            }
                        }
                    } else {
                        entry.items + newItem
                    }
                entry.copy(items = updatedItems)
            }.flatMap { shoppingRepository.save(it) }

    fun deleteItem(
        id: String,
        itemId: String,
    ): Mono<ShoppingEntry> =
        shoppingRepository
            .findById(id)
            .switchIfEmpty(Mono.error(NoSuchElementException("No shopping entry found")))
            .map { entry ->
                val updatedItems = entry.items.filterNot { it.id == itemId }

                if (updatedItems.size == entry.items.size) {
                    throw NoSuchElementException("Item not found")
                }

                entry.copy(items = updatedItems)
            }.flatMap { shoppingRepository.save(it) }

    fun updateItem(
        entryId: String,
        updatedItem: ShoppingItem,
    ): Mono<ShoppingEntry> =
        shoppingRepository
            .findById(entryId)
            .switchIfEmpty(Mono.error(NoSuchElementException("No shopping entry found")))
            .map { entry ->
                val updatedItems = entry.items.map { if (it.id == updatedItem.id) updatedItem else it }

                entry.copy(items = updatedItems)
            }.flatMap { shoppingRepository.save(it) }
}
