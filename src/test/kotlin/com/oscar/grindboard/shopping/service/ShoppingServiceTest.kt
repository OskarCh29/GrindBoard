package com.oscar.grindboard.shopping.service

import com.mongodb.assertions.Assertions.assertTrue
import com.oscar.grindboard.shopping.dto.ShoppingEntryRequest
import com.oscar.grindboard.shopping.dto.ShoppingItemRequest
import com.oscar.grindboard.shopping.dto.toDomain
import com.oscar.grindboard.shopping.model.ShoppingEntry
import com.oscar.grindboard.shopping.model.ShoppingItem
import com.oscar.grindboard.shopping.repository.ShoppingEntryRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import reactor.core.publisher.Mono
import reactor.kotlin.test.test
import java.math.BigDecimal
import java.time.LocalDate
import kotlin.test.assertEquals

class ShoppingServiceTest {
    private val repo = mockk<ShoppingEntryRepository>()
    private val service = ShoppingService(repo)

    @Test
    fun `should create shopping entry`() {
        every { repo.save(any()) } returns Mono.just(domainEntry())

        val result = service.addShoppingEntry(entryRequest()).block()

        assertNotNull(result)
        assertEquals("entry-1", result.id)
        assertEquals(1, result.items.size)

        verify(exactly = 1) { repo.save(any()) }
    }

    @Test
    fun `should throw exception when trying to make entry for the same day`() {
        every { repo.save(any()) } returns
            Mono.error(IllegalStateException("Entry for this date already exists"))

        service
            .addShoppingEntry(entryRequest())
            .test()
            .expectErrorMatches { ex ->
                ex is IllegalStateException && ex.message == "Entry for this date already exists"
            }.verify()
    }

    @Test
    fun `should add item to shopping entry`() {
        every { repo.findById("entry-1") } returns Mono.just(domainEntry())
        every { repo.save(any()) } answers { Mono.just(firstArg()) }

        val result =
            service
                .addItem("entry-1", itemRequest(id = "item-2", name = "Chicken", quantity = 2))
                .block()

        assertEquals(2, result!!.items.size)
        assertTrue(result.items.any { it.name == "Chicken" })
    }

    @Test
    fun `should add item when existing and increment quantity to shopping entry`() {
        every { repo.findById("entry-1") } returns Mono.just(domainEntry())
        every { repo.save(any()) } answers { Mono.just(firstArg()) }

        val result = service.addItem("entry-1", itemRequest(name = "Rice", quantity = 3)).block()

        val riceQuantity = result!!.items.first { it.name == "Rice" }.quantity

        assertEquals(4, riceQuantity)
    }

    @Test fun `should throw exception when adding item to non existing shopping entry`() {}

    @Test fun `should delete item from shopping entry`() {}

    @Test fun `should throw exception when deleting non existing item`() {}

    @Test fun `should throw exception when deleing from non existing shopping entry`() {}

    @Test fun `should update item on the shopping entry list`() {}

    @Test fun `should throw exception when trying to update non shopping entry`() {}

    @Test fun `should throw exception when deleting non existing item from shopping entry`() {}

    private fun itemRequest(
        id: String? = null,
        name: String = "Rice",
        quantity: Int = 1,
        price: BigDecimal = BigDecimal.ONE,
    ) = ShoppingItemRequest(itemId = id, name = name, quantity = quantity, price = price)

    private fun entryRequest(
        date: LocalDate = LocalDate.of(2026, 3, 15),
        items: List<ShoppingItemRequest> = listOf(itemRequest(id = "item-1")),
    ) = ShoppingEntryRequest(date = date, items = items)

    private fun domainEntry(): ShoppingEntry =
        entryRequest()
            .toDomain()
            .copy(
                id = "entry-1",
                items =
                    listOf(
                        ShoppingItem(
                            id = "item-1",
                            name = "Rice",
                            quantity = 1,
                            pricePerPiece = BigDecimal.ONE,
                        ),
                    ),
            )
}
