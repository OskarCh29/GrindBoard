package com.oscar.grindboard.shopping.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

@Schema(
    description =
        "Shopping entry containing necessary information regarding shopping for particular day",
)
data class ShoppingEntryResponse(
    @Schema(description = "Unique id of the shopping entry") val id: String,
    @Schema(description = "Date of the shopping entry") val date: LocalDate,
    @Schema(description = "List of the bought items for particular shopping day")
    val items: List<ShoppingItemResponse>,
)
