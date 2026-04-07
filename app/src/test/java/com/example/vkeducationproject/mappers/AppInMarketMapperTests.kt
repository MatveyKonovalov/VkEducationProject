package com.example.vkeducationproject.data.mappers

import com.example.vkeducationproject.data.dtomodels.AppInMarketDto
import com.example.vkeducationproject.domain.models.Category
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class AppInMarketMapperTest {

    @Mock
    private lateinit var categoryMapper: CategoryMapper

    private lateinit var appInMarketMapper: AppInMarketMapper

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        appInMarketMapper = AppInMarketMapper(categoryMapper)
    }

    @Test
    fun `toDomain should map all fields correctly`() {
        // Given
        val dto = createMockAppInMarketDto(
            id = "123",
            name = "Test Game",
            description = "This is a test game",
            iconUrl = "https://example.com/icon.png",
            category = "GAME"
        )

        val expectedCategory = Category.GAME
        `when`(categoryMapper.toDomain(dto.category)).thenReturn(expectedCategory)

        // When
        val result = appInMarketMapper.toDomain(dto)

        // Then
        assertEquals(dto.id, result.id)
        assertEquals(dto.name, result.name)
        assertEquals(dto.description, result.description)
        assertEquals(dto.iconUrl, result.iconUrl)
        assertEquals(expectedCategory, result.category)

        verify(categoryMapper, times(1)).toDomain(dto.category)
    }

    @Test
    fun `toDomain should handle empty name`() {
        // Given
        val dto = createMockAppInMarketDto(name = "")
        `when`(categoryMapper.toDomain(anyString())).thenReturn(Category.OTHER)

        // When
        val result = appInMarketMapper.toDomain(dto)

        // Then
        assertEquals("", result.name)
    }

    @Test
    fun `toDomain should handle empty description`() {
        // Given
        val dto = createMockAppInMarketDto(description = "")
        `when`(categoryMapper.toDomain(anyString())).thenReturn(Category.OTHER)

        // When
        val result = appInMarketMapper.toDomain(dto)

        // Then
        assertEquals("", result.description)
    }

    @Test
    fun `toDomain should handle empty iconUrl`() {
        // Given
        val dto = createMockAppInMarketDto(iconUrl = "")
        `when`(categoryMapper.toDomain(anyString())).thenReturn(Category.OTHER)

        // When
        val result = appInMarketMapper.toDomain(dto)

        // Then
        assertEquals("", result.iconUrl)
    }

    @Test
    fun `toDomain should handle all category types`() {
        // Given
        val categories = listOf(
            "GAME" to Category.GAME,
            "APP" to Category.APP,
            "MUSIC" to Category.MUSIC,
            "VIDEO" to Category.VIDEO,
            "unknown" to Category.OTHER
        )

        categories.forEach { (categoryString, expectedCategory) ->
            val dto = createMockAppInMarketDto(category = categoryString)
            `when`(categoryMapper.toDomain(categoryString)).thenReturn(expectedCategory)

            // When
            val result = appInMarketMapper.toDomain(dto)

            // Then
            assertEquals(expectedCategory, result.category)
        }
    }

    @Test
    fun `toDomain should handle special characters in name`() {
        // Given
        val specialName = "Game!@#$%^&*()_+™®©"
        val dto = createMockAppInMarketDto(name = specialName)
        `when`(categoryMapper.toDomain(anyString())).thenReturn(Category.GAME)

        // When
        val result = appInMarketMapper.toDomain(dto)

        // Then
        assertEquals(specialName, result.name)
    }

    @Test
    fun `toDomain should handle very long strings`() {
        // Given
        val longName = "A".repeat(1000)
        val longDescription = "B".repeat(5000)
        val dto = createMockAppInMarketDto(
            name = longName,
            description = longDescription
        )
        `when`(categoryMapper.toDomain(anyString())).thenReturn(Category.GAME)

        // When
        val result = appInMarketMapper.toDomain(dto)

        // Then
        assertEquals(longName, result.name)
        assertEquals(longDescription, result.description)
        assertEquals(1000, result.name.length)
        assertEquals(5000, result.description.length)
    }

    @Test
    fun `toDomain should handle unicode characters`() {
        // Given
        val unicodeName = "Игра на русском 😀🎮"
        val unicodeDescription = "Описание на русском языке with English"
        val dto = createMockAppInMarketDto(
            name = unicodeName,
            description = unicodeDescription
        )
        `when`(categoryMapper.toDomain(anyString())).thenReturn(Category.GAME)

        // When
        val result = appInMarketMapper.toDomain(dto)

        // Then
        assertEquals(unicodeName, result.name)
        assertEquals(unicodeDescription, result.description)
    }

    @Test
    fun `toDomain should verify category mapper is called with correct parameter`() {
        // Given
        val categoryString = "PRODUCTIVITY"
        val dto = createMockAppInMarketDto(category = categoryString)
        `when`(categoryMapper.toDomain(categoryString)).thenReturn(Category.PRODUCTIVITY)

        // When
        appInMarketMapper.toDomain(dto)

        // Then
        verify(categoryMapper, times(1)).toDomain(categoryString)
        verifyNoMoreInteractions(categoryMapper)
    }

    @Test
    fun `toDomain should handle null-like values in strings`() {
        // Given
        val dto = createMockAppInMarketDto(
            name = "null",
            description = "undefined",
            iconUrl = "none"
        )
        `when`(categoryMapper.toDomain(anyString())).thenReturn(Category.OTHER)

        // When
        val result = appInMarketMapper.toDomain(dto)

        // Then
        assertEquals("null", result.name)
        assertEquals("undefined", result.description)
        assertEquals("none", result.iconUrl)
    }


    @Test
    fun `toDomain should handle whitespace in all fields`() {
        // Given
        val dto = createMockAppInMarketDto(
            name = "  Game Name With Spaces  ",
            description = "  Description with spaces  ",
            iconUrl = "  https://example.com/icon.png  "
        )
        `when`(categoryMapper.toDomain(anyString())).thenReturn(Category.GAME)

        // When
        val result = appInMarketMapper.toDomain(dto)

        // Then
        assertEquals("  Game Name With Spaces  ", result.name)
        assertEquals("  Description with spaces  ", result.description)
        assertEquals("  https://example.com/icon.png  ", result.iconUrl)
    }

    @Test
    fun `toDomain should handle multiple calls with different DTOs`() {
        // Given
        val dto1 = createMockAppInMarketDto(id = "1", name = "App 1", category = "GAME")
        val dto2 = createMockAppInMarketDto(id = "2", name = "App 2", category = "MUSIC")

        `when`(categoryMapper.toDomain("GAME")).thenReturn(Category.GAME)
        `when`(categoryMapper.toDomain("MUSIC")).thenReturn(Category.MUSIC)

        // When
        val result1 = appInMarketMapper.toDomain(dto1)
        val result2 = appInMarketMapper.toDomain(dto2)

        // Then
        assertEquals("1", result1.id)
        assertEquals("App 1", result1.name)
        assertEquals(Category.GAME, result1.category)

        assertEquals("2", result2.id)
        assertEquals("App 2", result2.name)
        assertEquals(Category.MUSIC, result2.category)

        verify(categoryMapper, times(1)).toDomain("GAME")
        verify(categoryMapper, times(1)).toDomain("MUSIC")
    }
}

// Вспомогательная функция для создания тестовых данных
private fun createMockAppInMarketDto(
    id: String = "1",
    name: String = "Test App",
    description: String = "Test description",
    iconUrl: String = "https://example.com/icon.png",
    category: String = "GAME"
) = AppInMarketDto(
    id = id,
    name = name,
    description = description,
    iconUrl = iconUrl,
    category = category
)