package com.oscar.grindboard.shopping.model

import com.oscar.grindboard.shopping.dto.ShoppingItemResponse
import java.math.BigDecimal
import java.util.UUID

internal data class ShoppingItem(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val quantity: Int = 1,
    val pricePerPiece: BigDecimal,
)

internal fun ShoppingItem.toResponse(): ShoppingItemResponse =
    ShoppingItemResponse(
        id = this.id,
        name = this.name,
        quantity = this.quantity,
        pricePerOne = this.pricePerPiece,
    )
