package com.oscar.grindboard.shopping.dto

import com.oscar.grindboard.shopping.model.ShoppingEntry
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid
import org.jetbrains.annotations.NotNull
import java.time.LocalDate

@Schema(
    description =
        "Shopping entry containing necessary information regarding shopping for particular date",
)
data class ShoppingEntryRequest(
    @Schema(description = "Date of the shopping entry") @field:NotNull val date: LocalDate,
    @Schema(description = "List of the bought items for particular shopping day")
    @field:Valid
    val items: List<ShoppingItemRequest> = emptyList(),
)

fun ShoppingEntryRequest.toDomain(): ShoppingEntry = ShoppingEntry(date = this.date, items = this.items.map { it.toDomain() })
