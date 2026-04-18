package com.oscar.grindboard.shopping.model

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Schema(
    description =
        "Shopping entry containing necessary information regarding shopping for particular date",
)
@Document(collection = "shopping_entries")
data class ShoppingEntry(
    @Schema(description = "Entry unique ID") @Id val id: String? = null,
    @Schema(description = "Date of the shopping entry") val date: LocalDate,
    @Schema(description = "List of the bought items for particular shopping day")
    val items: List<ShoppingItem>,
)
