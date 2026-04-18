package com.oscar.grindboard.shopping.model

import com.oscar.grindboard.shopping.dto.ShoppingEntryResponse
import io.swagger.v3.oas.annotations.Hidden
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Hidden
@Document(collection = "shopping_entries")
@CompoundIndex(name = "unique_date", def = "{'date': 1}", unique = true)
data class ShoppingEntry(
    @Id val id: String? = null,
    val date: LocalDate,
    val items: List<ShoppingItem> = emptyList(),
)

fun ShoppingEntry.toResponse(): ShoppingEntryResponse =
    ShoppingEntryResponse(
        id = this.id!!,
        date = this.date,
        items = this.items.map { it.toResponse() },
    )
