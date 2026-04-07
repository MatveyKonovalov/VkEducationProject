package com.example.vkeducationproject.data.mappers

import com.example.vkeducationproject.domain.models.AgeRatings
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class AgeRatingMapperTest {

    private lateinit var mapper: AgeRatingMapper

    @Before
    fun setUp() {
        mapper = AgeRatingMapper()
    }

    // Тесты для toDomain (Int -> AgeRatings)

    @Test
    fun `toDomain should return PEGI3 for age between 3 and 6`() {
        // Given
        val ages = listOf(3, 4, 5, 6)

        // When & Then
        ages.forEach { age ->
            val result = mapper.toDomain(age)
            assertEquals(AgeRatings.PEGI3, result, "Age $age should map to PEGI3")
        }
    }

    @Test
    fun `toDomain should return PEGI7 for age between 7 and 11`() {
        // Given
        val ages = listOf(7, 8, 9, 10, 11)

        // When & Then
        ages.forEach { age ->
            val result = mapper.toDomain(age)
            assertEquals(AgeRatings.PEGI7, result, "Age $age should map to PEGI7")
        }
    }

    @Test
    fun `toDomain should return PEGI12 for age between 12 and 15`() {
        // Given
        val ages = listOf(12, 13, 14, 15)

        // When & Then
        ages.forEach { age ->
            val result = mapper.toDomain(age)
            assertEquals(AgeRatings.PEGI12, result, "Age $age should map to PEGI12")
        }
    }

    @Test
    fun `toDomain should return PEGI16 for age between 16 and 17`() {
        // Given
        val ages = listOf(16, 17)

        // When & Then
        ages.forEach { age ->
            val result = mapper.toDomain(age)
            assertEquals(AgeRatings.PEGI16, result, "Age $age should map to PEGI16")
        }
    }

    @Test
    fun `toDomain should return PEGI18 for age 18 and above`() {
        // Given
        val ages = listOf(18, 19, 20, 25, 30, 99, 100)

        // When & Then
        ages.forEach { age ->
            val result = mapper.toDomain(age)
            assertEquals(AgeRatings.PEGI18, result, "Age $age should map to PEGI18")
        }
    }

    @Test
    fun `toDomain should return PEGI18 for age 0 and below`() {
        // Given
        val ages = listOf(0, -1, -5, -10)

        // When & Then
        ages.forEach { age ->
            val result = mapper.toDomain(age)
            assertEquals(AgeRatings.PEGI18, result, "Age $age should map to PEGI18")
        }
    }

    @Test
    fun `toDomain should handle boundary values correctly`() {
        // Given & When & Then
        assertEquals(AgeRatings.PEGI3, mapper.toDomain(3))   // Минимальная граница PEGI3
        assertEquals(AgeRatings.PEGI7, mapper.toDomain(7))   // Минимальная граница PEGI7
        assertEquals(AgeRatings.PEGI12, mapper.toDomain(12)) // Минимальная граница PEGI12
        assertEquals(AgeRatings.PEGI16, mapper.toDomain(16)) // Минимальная граница PEGI16
        assertEquals(AgeRatings.PEGI18, mapper.toDomain(18)) // Минимальная граница PEGI18

        assertEquals(AgeRatings.PEGI3, mapper.toDomain(6))    // Максимальная граница PEGI3
        assertEquals(AgeRatings.PEGI7, mapper.toDomain(11))   // Максимальная граница PEGI7
        assertEquals(AgeRatings.PEGI12, mapper.toDomain(15))  // Максимальная граница PEGI12
        assertEquals(AgeRatings.PEGI16, mapper.toDomain(17))  // Максимальная граница PEGI16
    }

    // Тесты для toEntity (AgeRatings -> Int)

    @Test
    fun `toEntity should return 3 for PEGI3`() {
        // When
        val result = mapper.toEntity(AgeRatings.PEGI3)

        // Then
        assertEquals(3, result)
    }

    @Test
    fun `toEntity should return 7 for PEGI7`() {
        // When
        val result = mapper.toEntity(AgeRatings.PEGI7)

        // Then
        assertEquals(7, result)
    }

    @Test
    fun `toEntity should return 12 for PEGI12`() {
        // When
        val result = mapper.toEntity(AgeRatings.PEGI12)

        // Then
        assertEquals(12, result)
    }

    @Test
    fun `toEntity should return 16 for PEGI16`() {
        // When
        val result = mapper.toEntity(AgeRatings.PEGI16)

        // Then
        assertEquals(16, result)
    }

    @Test
    fun `toEntity should return 18 for PEGI18`() {
        // When
        val result = mapper.toEntity(AgeRatings.PEGI18)

        // Then
        assertEquals(18, result)
    }

    @Test
    fun `toEntity should handle all enum values`() {
        // Given
        val allRatings = listOf(
            AgeRatings.PEGI3 to 3,
            AgeRatings.PEGI7 to 7,
            AgeRatings.PEGI12 to 12,
            AgeRatings.PEGI16 to 16,
            AgeRatings.PEGI18 to 18
        )

        // When & Then
        allRatings.forEach { (rating, expectedValue) ->
            val result = mapper.toEntity(rating)
            assertEquals(expectedValue, result, "Rating $rating should map to $expectedValue")
        }
    }

    // Тесты на консистентность (прямое и обратное преобразование)

    @Test
    fun `toDomain and toEntity should be consistent for valid ages`() {
        // Given
        val ages = listOf(3, 7, 12, 16, 18)

        // When & Then
        ages.forEach { age ->
            val domain = mapper.toDomain(age)
            val backToInt = mapper.toEntity(domain)
            assertEquals(age, backToInt, "Age $age should convert back to same value")
        }
    }

    @Test
    fun `toEntity and toDomain should be consistent for all age ratings`() {
        // Given
        val ratings = AgeRatings.values().toList()

        // When & Then
        ratings.forEach { rating ->
            val intValue = mapper.toEntity(rating)
            val backToRating = mapper.toDomain(intValue)
            assertEquals(rating, backToRating, "Rating $rating should convert back to same value")
        }
    }

    @Test
    fun `toDomain should be idempotent for boundary values`() {
        // Given
        val boundaryValues = listOf(3, 7, 12, 16, 18)

        // When & Then
        boundaryValues.forEach { age ->
            val firstConversion = mapper.toDomain(age)
            val secondConversion = mapper.toDomain(mapper.toEntity(firstConversion))
            assertEquals(firstConversion, secondConversion, "Double conversion should return same result for age $age")
        }
    }

    // Тесты на производительность (проверка большого диапазона)

    @Test
    fun `toDomain should handle large range of ages without exception`() {
        // Given
        val ages = (-1000..1000).toList()

        // When & Then
        ages.forEach { age ->
            val result = mapper.toDomain(age)
            assert(result in AgeRatings.values().toList())
        }
    }
}