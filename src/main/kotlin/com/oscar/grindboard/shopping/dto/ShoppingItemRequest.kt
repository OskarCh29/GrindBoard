package com.oscar.grindboard.shopping.dto

import com.oscar.grindboard.shopping.model.ShoppingItem
import com.oscar.grindboard.shopping.validator.Required
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Positive
import java.math.BigDecimal

@Schema(description = "Instance representing particular shopping item")
data class ShoppingItemRequest(
    @Schema(description = "Particular shopping item id for item operations")
    val itemId: String? = null,
    @field:Required @Schema(description = "Name of the shopping item") val name: String,
    @field:Min(1) @Schema(description = "Quantity of the shopping item") val quantity: Int,
    @field:Positive
    @Schema(description = "Price of the shopping item - per one")
    val price: BigDecimal,
)

internal fun ShoppingItemRequest.toDomain(): ShoppingItem =
    ShoppingItem(
        name = this.name,
        quantity = this.quantity,
        pricePerPiece = this.price,
    )
