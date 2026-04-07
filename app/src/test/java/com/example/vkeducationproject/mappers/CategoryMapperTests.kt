package com.example.vkeducationproject.data.mappers

import com.example.vkeducationproject.domain.models.Category
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class CategoryMapperTest {

    private lateinit var mapper: CategoryMapper

    @Before
    fun setUp() {
        mapper = CategoryMapper()
    }

    // Тесты для основных категорий

    @Test
    fun `toDomain should map приложения to APP`() {
        val result = mapper.toDomain("приложения")
        assertEquals(Category.APP, result)
    }

    @Test
    fun `toDomain should map игры to GAME`() {
        val result = mapper.toDomain("игры")
        assertEquals(Category.GAME, result)
    }

    @Test
    fun `toDomain should map производительность to PRODUCTIVITY`() {
        val result = mapper.toDomain("производительность")
        assertEquals(Category.PRODUCTIVITY, result)
    }

    @Test
    fun `toDomain should map социальные сети to SOCIAL`() {
        val result = mapper.toDomain("социальные сети")
        assertEquals(Category.SOCIAL, result)
    }

    @Test
    fun `toDomain should map образование to EDUCATION`() {
        val result = mapper.toDomain("образование")
        assertEquals(Category.EDUCATION, result)
    }

    @Test
    fun `toDomain should map развлечения to ENTERTAINMENT`() {
        val result = mapper.toDomain("развлечения")
        assertEquals(Category.ENTERTAINMENT, result)
    }

    @Test
    fun `toDomain should map музыка to MUSIC`() {
        val result = mapper.toDomain("музыка")
        assertEquals(Category.MUSIC, result)
    }

    @Test
    fun `toDomain should map видео to VIDEO`() {
        val result = mapper.toDomain("видео")
        assertEquals(Category.VIDEO, result)
    }

    @Test
    fun `toDomain should map фотография to PHOTOGRAPHY`() {
        val result = mapper.toDomain("фотография")
        assertEquals(Category.PHOTOGRAPHY, result)
    }

    @Test
    fun `toDomain should map здоровье to HEALTH`() {
        val result = mapper.toDomain("здоровье")
        assertEquals(Category.HEALTH, result)
    }

    @Test
    fun `toDomain should map спорт to SPORTS`() {
        val result = mapper.toDomain("спорт")
        assertEquals(Category.SPORTS, result)
    }

    @Test
    fun `toDomain should map новости to NEWS`() {
        val result = mapper.toDomain("новости")
        assertEquals(Category.NEWS, result)
    }

    @Test
    fun `toDomain should map книги to BOOKS`() {
        val result = mapper.toDomain("книги")
        assertEquals(Category.BOOKS, result)
    }

    @Test
    fun `toDomain should map бизнес to BUSINESS`() {
        val result = mapper.toDomain("бизнес")
        assertEquals(Category.BUSINESS, result)
    }

    @Test
    fun `toDomain should map финансы to FINANCE`() {
        val result = mapper.toDomain("финансы")
        assertEquals(Category.FINANCE, result)
    }

    @Test
    fun `toDomain should map путешествия to TRAVEL`() {
        val result = mapper.toDomain("путешествия")
        assertEquals(Category.TRAVEL, result)
    }

    @Test
    fun `toDomain should map карты to MAPS`() {
        val result = mapper.toDomain("карты")
        assertEquals(Category.MAPS, result)
    }

    @Test
    fun `toDomain should map еда to FOOD`() {
        val result = mapper.toDomain("еда")
        assertEquals(Category.FOOD, result)
    }

    @Test
    fun `toDomain should map покупки to SHOPPING`() {
        val result = mapper.toDomain("покупки")
        assertEquals(Category.SHOPPING, result)
    }

    @Test
    fun `toDomain should map утилиты to UTILITIES`() {
        val result = mapper.toDomain("утилиты")
        assertEquals(Category.UTILITIES, result)
    }

    // Тесты для составных категорий

    @Test
    fun `toDomain should map здоровье и фитнес to HEALTH_FITNESS`() {
        val result = mapper.toDomain("здоровье и фитнес")
        assertEquals(Category.HEALTH_FITNESS, result)
    }

    @Test
    fun `toDomain should map фото и видео to PHOTO_VIDEO`() {
        val result = mapper.toDomain("фото и видео")
        assertEquals(Category.PHOTO_VIDEO, result)
    }

    @Test
    fun `toDomain should map еда и напитки to FOOD_DRINK`() {
        val result = mapper.toDomain("еда и напитки")
        assertEquals(Category.FOOD_DRINK, result)
    }

    @Test
    fun `toDomain should map образ жизни to LIFESTYLE`() {
        val result = mapper.toDomain("образ жизни")
        assertEquals(Category.LIFESTYLE, result)
    }

    @Test
    fun `toDomain should map навигация to NAVIGATION`() {
        val result = mapper.toDomain("навигация")
        assertEquals(Category.NAVIGATION, result)
    }

    @Test
    fun `toDomain should map общение to COMMUNICATION`() {
        val result = mapper.toDomain("общение")
        assertEquals(Category.COMMUNICATION, result)
    }

    @Test
    fun `toDomain should map погода to WEATHER`() {
        val result = mapper.toDomain("погода")
        assertEquals(Category.WEATHER, result)
    }

    @Test
    fun `toDomain should map книги и справочники to BOOKS_REFERENCE`() {
        val result = mapper.toDomain("книги и справочники")
        assertEquals(Category.BOOKS_REFERENCE, result)
    }

    // Тесты для регистронезависимости и пробелов

    @Test
    fun `toDomain should be case insensitive`() {
        val testCases = listOf(
            "ИГРЫ" to Category.GAME,
            "Игры" to Category.GAME,
            "иГрЫ" to Category.GAME,
            "МУЗЫКА" to Category.MUSIC,
            "Музыка" to Category.MUSIC,
            "мУзЫкА" to Category.MUSIC
        )

        testCases.forEach { (input, expected) ->
            val result = mapper.toDomain(input)
            assertEquals(expected, result, "Failed for input: $input")
        }
    }

    @Test
    fun `toDomain should handle leading and trailing spaces`() {
        val testCases = listOf(
            "  игры  " to Category.GAME,
            "  музыка " to Category.MUSIC,
            "   приложения   " to Category.APP,
            " социальные сети " to Category.SOCIAL
        )

        testCases.forEach { (input, expected) ->
            val result = mapper.toDomain(input)
            assertEquals(expected, result, "Failed for input: '$input'")
        }
    }

    @Test
    fun `toDomain should handle mixed case with spaces`() {
        val result = mapper.toDomain("  Здоровье И Фитнес  ")
        assertEquals(Category.HEALTH_FITNESS, result)
    }

    // Тесты для неизвестных категорий

    @Test
    fun `toDomain should return OTHER for unknown category`() {
        val result = mapper.toDomain("неизвестная категория")
        assertEquals(Category.OTHER, result)
    }

    @Test
    fun `toDomain should return OTHER for empty string`() {
        val result = mapper.toDomain("")
        assertEquals(Category.OTHER, result)
    }

    @Test
    fun `toDomain should return OTHER for string with only spaces`() {
        val result = mapper.toDomain("   ")
        assertEquals(Category.OTHER, result)
    }

    @Test
    fun `toDomain should return OTHER for null-like strings`() {
        val testCases = listOf(
            "null",
            "undefined",
            "none",
            "N/A",
            "unknown"
        )

        testCases.forEach { input ->
            val result = mapper.toDomain(input)
            assertEquals(Category.OTHER, result, "Failed for input: $input")
        }
    }

    // Тесты на все возможные категории

    @Test
    fun `toDomain should handle all defined categories`() {
        val categoriesMap = mapOf(
            "приложения" to Category.APP,
            "игры" to Category.GAME,
            "производительность" to Category.PRODUCTIVITY,
            "социальные сети" to Category.SOCIAL,
            "образование" to Category.EDUCATION,
            "развлечения" to Category.ENTERTAINMENT,
            "музыка" to Category.MUSIC,
            "видео" to Category.VIDEO,
            "фотография" to Category.PHOTOGRAPHY,
            "здоровье" to Category.HEALTH,
            "спорт" to Category.SPORTS,
            "новости" to Category.NEWS,
            "книги" to Category.BOOKS,
            "бизнес" to Category.BUSINESS,
            "финансы" to Category.FINANCE,
            "путешествия" to Category.TRAVEL,
            "карты" to Category.MAPS,
            "еда" to Category.FOOD,
            "покупки" to Category.SHOPPING,
            "утилиты" to Category.UTILITIES,
            "здоровье и фитнес" to Category.HEALTH_FITNESS,
            "фото и видео" to Category.PHOTO_VIDEO,
            "еда и напитки" to Category.FOOD_DRINK,
            "образ жизни" to Category.LIFESTYLE,
            "навигация" to Category.NAVIGATION,
            "общение" to Category.COMMUNICATION,
            "погода" to Category.WEATHER,
            "книги и справочники" to Category.BOOKS_REFERENCE
        )

        categoriesMap.forEach { (input, expected) ->
            val result = mapper.toDomain(input)
            assertEquals(expected, result, "Failed for input: $input")
        }
    }

}