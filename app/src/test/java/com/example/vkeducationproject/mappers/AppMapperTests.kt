package com.example.vkeducationproject.data.mappers

import com.example.vkeducationproject.data.dtomodels.AppDto
import com.example.vkeducationproject.domain.models.AgeRatings
import com.example.vkeducationproject.domain.models.App
import com.example.vkeducationproject.domain.models.Category
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class AppMapperTest {

    @Mock
    private lateinit var ageRatingMapper: AgeRatingMapper

    @Mock
    private lateinit var categoryMapper: CategoryMapper

    private lateinit var appMapper: AppMapper

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        appMapper = AppMapper(ageRatingMapper, categoryMapper)
    }

    @Test
    fun `toDomain should map all fields correctly`() {
        // Given
        val dto = createMockAppDto(
            id = "123",
            name = "Test Game",
            developer = "Test Developer",
            category = "GAME",
            ageRating = 12,
            size = 150.5f,
            iconUrl = "https://example.com/icon.png",
            screenshotUrlList = listOf("url1", "url2", "url3"),
            description = "This is a test game description"
        )

        val expectedCategory = Category.GAME
        val expectedAgeRating = AgeRatings.PEGI12

        `when`(categoryMapper.toDomain(dto.category)).thenReturn(expectedCategory)
        `when`(ageRatingMapper.toDomain(dto.ageRating)).thenReturn(expectedAgeRating)

        // When
        val result = appMapper.toDomain(dto)

        // Then
        assertEquals(dto.id, result.id)
        assertEquals(dto.name, result.name)
        assertEquals(dto.developer, result.developer)
        assertEquals(expectedCategory, result.category)
        assertEquals(expectedAgeRating, result.ageRating)
        assertEquals(dto.size, result.size)
        assertEquals(dto.iconUrl, result.iconUrl)
        assertEquals(dto.screenshotUrlList, result.screenshotUrlList)
        assertEquals(dto.description, result.description)

        verify(categoryMapper, times(1)).toDomain(dto.category)
        verify(ageRatingMapper, times(1)).toDomain(dto.ageRating)
    }

    @Test
    fun `toDomain should handle empty screenshot list`() {
        // Given
        val dto = createMockAppDto(
            screenshotUrlList = emptyList()
        )

        `when`(categoryMapper.toDomain(anyString())).thenReturn(Category.BOOKS)
        `when`(ageRatingMapper.toDomain(anyInt())).thenReturn(AgeRatings.PEGI3)

        // When
        val result = appMapper.toDomain(dto)

        // Then
        assertNotNull(result.screenshotUrlList)
        assertEquals(0, result.screenshotUrlList.size)
    }

    @Test
    fun `toDomain should handle very long description`() {
        // Given
        val longDescription = "A".repeat(10000)
        val dto = createMockAppDto(
            description = longDescription
        )

        `when`(categoryMapper.toDomain(anyString())).thenReturn(Category.GAME)
        `when`(ageRatingMapper.toDomain(anyInt())).thenReturn(AgeRatings.PEGI12)

        // When
        val result = appMapper.toDomain(dto)

        // Then
        assertEquals(longDescription, result.description)
        assertEquals(10000, result.description.length)
    }

    @Test
    fun `toDomain should handle special characters in name and developer`() {
        // Given
        val specialName = "Game!@#$%^&*()_+"
        val specialDeveloper = "Developer™®©"
        val dto = createMockAppDto(
            name = specialName,
            developer = specialDeveloper
        )

        `when`(categoryMapper.toDomain(anyString())).thenReturn(Category.GAME)
        `when`(ageRatingMapper.toDomain(anyInt())).thenReturn(AgeRatings.PEGI12)

        // When
        val result = appMapper.toDomain(dto)

        // Then
        assertEquals(specialName, result.name)
        assertEquals(specialDeveloper, result.developer)
    }

    @Test
    fun `toDomain should handle all category types`() {
        // Given
        val categories = listOf("GAME", "COMMUNICATION", "BOOKS", "MUSIC", "VIDEO")
        val expectedCategories = listOf(
            Category.GAME,
            Category.COMMUNICATION,
            Category.BOOKS,
            Category.MUSIC,
            Category.VIDEO
        )

        categories.forEachIndexed { index, categoryString ->
            val dto = createMockAppDto(category = categoryString)
            `when`(categoryMapper.toDomain(categoryString)).thenReturn(expectedCategories[index])
            `when`(ageRatingMapper.toDomain(anyInt())).thenReturn(AgeRatings.PEGI12)

            // When
            val result = appMapper.toDomain(dto)

            // Then
            assertEquals(expectedCategories[index], result.category)
        }
    }

    @Test
    fun `toDomain should handle all age rating values`() {
        // Given
        val ages = listOf(3, 7, 12, 16, 18)
        val expectedRatings = listOf(
            AgeRatings.PEGI3,
            AgeRatings.PEGI7,
            AgeRatings.PEGI12,
            AgeRatings.PEGI16,
            AgeRatings.PEGI18
        )

        ages.forEachIndexed { index, age ->
            val dto = createMockAppDto(ageRating = age)
            `when`(categoryMapper.toDomain(anyString())).thenReturn(Category.GAME)
            `when`(ageRatingMapper.toDomain(age)).thenReturn(expectedRatings[index])

            // When
            val result = appMapper.toDomain(dto)

            // Then
            assertEquals(expectedRatings[index], result.ageRating)
        }
    }

    @Test
    fun `toDomain should handle zero size`() {
        // Given
        val dto = createMockAppDto(size = 0.0f)

        `when`(categoryMapper.toDomain(anyString())).thenReturn(Category.GAME)
        `when`(ageRatingMapper.toDomain(anyInt())).thenReturn(AgeRatings.PEGI3)

        // When
        val result = appMapper.toDomain(dto)

        // Then
        assertEquals(0.0f, result.size)
    }


    @Test
    fun `toDomain should verify mapper calls with correct parameters`() {
        // Given
        val dto = createMockAppDto(
            category = "GAME",
            ageRating = 12
        )

        `when`(categoryMapper.toDomain("GAME")).thenReturn(Category.GAME)
        `when`(ageRatingMapper.toDomain(12)).thenReturn(AgeRatings.PEGI12)

        // When
        appMapper.toDomain(dto)

        // Then
        verify(categoryMapper, times(1)).toDomain("GAME")
        verify(ageRatingMapper, times(1)).toDomain(12)
        verifyNoMoreInteractions(categoryMapper, ageRatingMapper)
    }


}

// Вспомогательная функция для создания тестовых данных
private fun createMockAppDto(
    id: String = "1",
    name: String = "Test App",
    developer: String = "Test Developer",
    category: String = "GAME",
    ageRating: Int = 12,
    size: Float = 100.5f,
    iconUrl: String = "https://example.com/icon.png",
    screenshotUrlList: List<String> = listOf("url1", "url2"),
    description: String = "Test description"
) = AppDto(
    id = id,
    name = name,
    developer = developer,
    category = category,
    ageRating = ageRating,
    size = size,
    iconUrl = iconUrl,
    screenshotUrlList = screenshotUrlList,
    description = description
)