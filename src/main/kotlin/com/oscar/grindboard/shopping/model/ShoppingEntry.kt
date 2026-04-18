package com.oscar.grindboard.shopping.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.LocalDate

@Document(collection = "shopping_entries")
data class ShoppingEntry(
    @Id val id: String? = null,
    val date: LocalDate,
    val items: List<ShoppingItem>,
    val totalAmount: BigDecimal? = null,
)
