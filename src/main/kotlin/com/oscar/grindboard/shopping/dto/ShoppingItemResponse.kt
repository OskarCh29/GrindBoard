package com.oscar.grindboard.shopping.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal

@Schema(description = "Instance representing particular shopping item")
data class ShoppingItemResponse(
    @Schema(description = "Particular shopping item id for item operations") val id: String,
    @Schema(description = "Name of the shopping item") val name: String,
    @Schema(description = "Quantity of the shopping item") val quantity: Int,
    @Schema(description = "Price of the shopping item - per one") val pricePerOne: BigDecimal,
)
