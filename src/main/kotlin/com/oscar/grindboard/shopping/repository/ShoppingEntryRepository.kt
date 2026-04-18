package com.oscar.grindboard.shopping.repository

import com.oscar.grindboard.shopping.model.ShoppingEntry
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import java.time.LocalDate

interface ShoppingEntryRepository : ReactiveMongoRepository<ShoppingEntry, String> {
    fun findByDate(date: LocalDate): Flux<ShoppingEntry>
}
