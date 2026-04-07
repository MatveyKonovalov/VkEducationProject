package com.example.vkeducationproject.data.repository

import com.example.vkeducationproject.data.datasources.MakeTestData
import com.example.vkeducationproject.data.datasources.local.AppDetailsDao
import com.example.vkeducationproject.data.datasources.local.AppDetailsEntity
import com.example.vkeducationproject.data.datasources.local.AppDetailsEntityMapper
import com.example.vkeducationproject.data.mappers.AppInMarketMapper
import com.example.vkeducationproject.data.mappers.AppMapper
import com.example.vkeducationproject.domain.models.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class AppRepositoryImplTest {

    @Mock
    private lateinit var mapper: AppMapper

    @Mock
    private lateinit var mapperInMarket: AppInMarketMapper

    @Mock
    private lateinit var makeTest: MakeTestData

    @Mock
    private lateinit var dao: AppDetailsDao

    @Mock
    private lateinit var mapperEntity: AppDetailsEntityMapper

    private lateinit var repository: AppRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = AppRepositoryImpl(
            mapper = mapper,
            mapperInMarket = mapperInMarket,
            makeTest = makeTest,
            dao = dao,
            mapperEntity = mapperEntity
        )
    }

    @Test
    fun `1 - getApps should return empty list when API fails`() = runTest {
        // Given
        `when`(makeTest.makeData()).thenThrow(RuntimeException("Network error"))

        // When
        val result = repository.getApps()

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `2 - getAppById should return default app when exception occurs`() = runTest {
        // Given
        val appId = "123"
        `when`(dao.getApp(appId)).thenThrow(RuntimeException("Database error"))

        // When
        val result = repository.getAppById(appId)

        // Then
        assertEquals(repository.getDefaultApp(), result)
    }

    @Test
    fun `3 - getDefaultApp should return correct app with all fields`() {
        // When
        val defaultApp = repository.getDefaultApp()

        // Then
        assertEquals("1", defaultApp.id)
        assertEquals("Гильдия Героев: Экшен ММО РПГ", defaultApp.name)
        assertEquals("VK Play", defaultApp.developer)
        assertEquals(Category.GAME, defaultApp.category)
        assertEquals(AgeRatings.PEGI12, defaultApp.ageRating)
        assertEquals(223.7f, defaultApp.size)
        assertEquals(4, defaultApp.screenshotUrlList.size)
        assertTrue(defaultApp.description.isNotEmpty())
    }

    @Test
    fun `5 - toggleWishlist should change status from true to false`() = runTest {
        // Given
        val appId = "123"
        val mockEntity = createMockAppDetailsEntity(appId, isInWishlist = true)
        `when`(dao.getApp(appId)).thenReturn(listOf(mockEntity))

        // When
        repository.toggleWishlist(appId)

        // Then
        verify(dao, times(1)).updateWishlistStatus(appId, false)
    }



    @Test
    fun `6 - getAppById should handle multiple different ids correctly`() = runTest {
        // Given
        val appId1 = "1"
        val appId2 = "2"
        val mockDto1 = createMockAppDto(appId1)
        val mockDto2 = createMockAppDto(appId2)
        val mockDomain1 = createMockApp(appId1)
        val mockDomain2 = createMockApp(appId2)
        val mockEntity1 = createMockAppDetailsEntity(appId1)
        val mockEntity2 = createMockAppDetailsEntity(appId2)

        `when`(dao.getApp(appId1)).thenReturn(emptyList())
        `when`(dao.getApp(appId2)).thenReturn(emptyList())
        `when`(makeTest.getAppById(appId1)).thenReturn(mockDto1)
        `when`(makeTest.getAppById(appId2)).thenReturn(mockDto2)
        `when`(mapper.toDomain(mockDto1)).thenReturn(mockDomain1)
        `when`(mapper.toDomain(mockDto2)).thenReturn(mockDomain2)
        `when`(mapperEntity.toEntity(mockDomain1)).thenReturn(mockEntity1)
        `when`(mapperEntity.toEntity(mockDomain2)).thenReturn(mockEntity2)

        // When
        val result1 = repository.getAppById(appId1)
        val result2 = repository.getAppById(appId2)

        // Then
        assertEquals(mockDomain1, result1)
        assertEquals(mockDomain2, result2)
        verify(dao, times(1)).insert(mockEntity1)
        verify(dao, times(1)).insert(mockEntity2)
    }
}

// Вспомогательные функции для создания тестовых данных
private fun createMockAppDto(id: String = "1") = com.example.vkeducationproject.data.dtomodels.AppDto(
    id = id,
    name = "Test App $id",
    developer = "Test Developer",
    category = "GAME",
    ageRating = 12,
    size = 100.5f,
    screenshotUrlList = listOf("url1", "url2"),
    iconUrl = "icon_url",
    description = "Test description"
)

private fun createMockAppInMarket(id: String = "1") = AppInMarket(
    id = id,
    name = "Test App $id",
    category = Category.GAME,
    iconUrl = "icon_url",
    description=""
)

private fun createMockApp(id: String = "1") = App(
    id = id,
    name = "Test App $id",
    developer = "Test Developer",
    category = Category.GAME,
    ageRating = AgeRatings.PEGI12,
    size = 100.5f,
    screenshotUrlList = listOf("url1", "url2"),
    iconUrl = "icon_url",
    description = "Test description"
)

private fun createMockAppDetailsEntity(id: String = "1", isInWishlist: Boolean = false) =
    AppDetailsEntity(
        id = id,
        name = "Test App $id",
        developer = "Test Developer",
        category = Category.GAME,
        ageRating = 12,
        size = 100.5f,
        iconUrl = "icon_url",
        description = "Test description",
        isInWishlist = isInWishlist
    )