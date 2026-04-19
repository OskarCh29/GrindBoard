package com.oscar.grindboard.shopping.service

import com.mongodb.DuplicateKeyException
import com.oscar.grindboard.shopping.dto.ShoppingEntryRequest
import com.oscar.grindboard.shopping.dto.ShoppingEntryResponse
import com.oscar.grindboard.shopping.dto.ShoppingItemRequest
import com.oscar.grindboard.shopping.dto.toDomain
import com.oscar.grindboard.shopping.model.toResponse
import com.oscar.grindboard.shopping.repository.ShoppingEntryRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.LocalDate

// TODO: Replace illegal state with custom exception
@Service
internal class ShoppingService(
    private val shoppingRepository: ShoppingEntryRepository,
) {
    fun addShoppingEntry(entry: ShoppingEntryRequest): Mono<ShoppingEntryResponse> =
        shoppingRepository
            .save(entry.toDomain())
            .map { shoppingEntry -> shoppingEntry.toResponse() }
            .onErrorMap(DuplicateKeyException::class.java) {
                IllegalStateException("Entry for this date already exists")
            }

    fun getByDate(date: LocalDate): Mono<ShoppingEntryResponse> =
        shoppingRepository.findByDate(date).map { shoppingEntry -> shoppingEntry.toResponse() }

    fun delete(id: String): Mono<Void> = shoppingRepository.deleteById(id)

    fun addItem(
        id: String,
        newItem: ShoppingItemRequest,
    ): Mono<ShoppingEntryResponse> =
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
                        entry.items + newItem.toDomain()
                    }
                entry.copy(items = updatedItems)
            }.flatMap {
                shoppingRepository.save(it).map { shoppingEntry -> shoppingEntry.toResponse() }
            }

    fun deleteItem(
        id: String,
        itemId: String,
    ): Mono<ShoppingEntryResponse> =
        shoppingRepository
            .findById(id)
            .switchIfEmpty(Mono.error(NoSuchElementException("No shopping entry found")))
            .map { entry ->
                val updatedItems = entry.items.filterNot { it.id == itemId }

                val exists = entry.items.any { item -> item.id == itemId }
                if (!exists) {
                    throw NoSuchElementException("Item not found")
                }

                entry.copy(items = updatedItems)
            }.flatMap {
                shoppingRepository.save(it).map { shoppingEntry -> shoppingEntry.toResponse() }
            }

    fun updateItem(
        entryId: String,
        updatedItem: ShoppingItemRequest,
    ): Mono<ShoppingEntryResponse> =
        shoppingRepository
            .findById(entryId)
            .switchIfEmpty(Mono.error(NoSuchElementException("No shopping entry found")))
            .map { entry ->
                val exists = entry.items.any { it.id == updatedItem.itemId }
                if (!exists) {
                    throw NoSuchElementException("Item not found")
                }

                val updatedItems =
                    entry.items.map {
                        if (it.id == updatedItem.itemId) {
                            it.copy(
                                name = updatedItem.name,
                                quantity = updatedItem.quantity,
                                pricePerPiece = updatedItem.price,
                            )
                        } else {
                            it
                        }
                    }

                entry.copy(items = updatedItems)
            }.flatMap {
                shoppingRepository.save(it).map { shoppingEntry -> shoppingEntry.toResponse() }
            }
}
