package com.oscar.grindboard.shopping.model

import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal
import java.util.UUID

@Schema(description = "Instance representing particular shopping item")
data class ShoppingItem(
    @Schema(description = "Particular shopping item id")
    val id: String = UUID.randomUUID().toString(),
    @Schema(description = "Name of the shopping item") val name: String,
    @Schema(description = "Quantity of the shopping item") val quantity: Int = 1,
    @Schema(description = "Price of the shopping item - per one") val price: BigDecimal? = null,
)
