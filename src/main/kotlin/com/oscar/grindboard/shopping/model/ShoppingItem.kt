package com.oscar.grindboard.shopping.model

import java.math.BigDecimal

data class ShoppingItem(
    val name: String,
    val quantity: Int = 1,
    val price: BigDecimal? = null,
)
