package com.oscar.grindboard.shopping.service

import com.oscar.grindboard.shopping.model.ShoppingEntry
import com.oscar.grindboard.shopping.repository.ShoppingEntryRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDate

@Service
class ShoppingEntryService(
    private val shoppingRepository: ShoppingEntryRepository,
) {
    fun addShoppingEntry(shoppingEntry: ShoppingEntry): Mono<ShoppingEntry> = shoppingRepository.save(shoppingEntry)

    fun getByDate(date: LocalDate): Flux<ShoppingEntry> = shoppingRepository.findByDate(date)

    fun delete(id: String): Mono<Void> = shoppingRepository.deleteById(id)
}
