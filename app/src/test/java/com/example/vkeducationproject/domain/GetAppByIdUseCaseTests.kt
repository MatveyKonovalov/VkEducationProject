package com.example.vkeducationproject.domain.usecases

import com.example.vkeducationproject.domain.AppRepository
import com.example.vkeducationproject.domain.models.AgeRatings
import com.example.vkeducationproject.domain.models.App
import com.example.vkeducationproject.domain.models.Category
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class GetAppByIdUseCaseTest {

    @Mock
    private lateinit var repository: AppRepository

    private lateinit var getAppByIdUseCase: GetAppByIdUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getAppByIdUseCase = GetAppByIdUseCase(repository)
    }

    @Test
    fun `invoke should return Success with app when repository returns app by id`() = runTest {
        // Given
        val appId = "123"
        val expectedApp = App(
            id = appId,
            name = "Test App",
            developer = "Test Developer",
            category = Category.GAME,
            ageRating = AgeRatings.PEGI12,
            size = 100.5f,
            screenshotUrlList = listOf("url1", "url2"),
            iconUrl = "icon_url",
            description = "Test description"
        )
        `when`(repository.getAppById(appId)).thenReturn(expectedApp)

        // When
        val result = getAppByIdUseCase(appId)

        // Then
        assert(result is Result.Success)
        assert((result as Result.Success).data == expectedApp)
        verify(repository, times(1)).getAppById(appId)
    }

    @Test
    fun `invoke should return Error when repository throws exception`() = runTest {
        // Given
        val appId = "123"
        val exception = RuntimeException("App not found")
        `when`(repository.getAppById(appId)).thenThrow(exception)

        // When
        val result = getAppByIdUseCase(appId)

        // Then
        assert(result is Result.Error)
        assert((result as Result.Error).message == "App not found")
        verify(repository, times(1)).getAppById(appId)
    }

    @Test
    fun `invoke should return Error with default message when exception has null message`() = runTest {
        // Given
        val appId = "123"
        val exception = RuntimeException()
        `when`(repository.getAppById(appId)).thenThrow(exception)

        // When
        val result = getAppByIdUseCase(appId)

        // Then
        assert(result is Result.Error)
        assert((result as Result.Error).message == "Failed to load app")
        verify(repository, times(1)).getAppById(appId)
    }

    @Test
    fun `invoke should handle different app ids correctly`() = runTest {
        // Given
        val appId1 = "1"
        val appId2 = "2"
        val app1 = App(
            id = appId1,
            name = "App 1",
            developer = "Developer 1",
            category = Category.GAME,
            ageRating = AgeRatings.PEGI12,
            size = 100f,
            screenshotUrlList = emptyList(),
            iconUrl = "icon1",
            description = "Description 1"
        )
        val app2 = App(
            id = appId2,
            name = "App 2",
            developer = "Developer 2",
            category = Category.GAME,
            ageRating = AgeRatings.PEGI16,
            size = 200f,
            screenshotUrlList = emptyList(),
            iconUrl = "icon2",
            description = "Description 2"
        )

        `when`(repository.getAppById(appId1)).thenReturn(app1)
        `when`(repository.getAppById(appId2)).thenReturn(app2)

        // When
        val result1 = getAppByIdUseCase(appId1)
        val result2 = getAppByIdUseCase(appId2)

        // Then
        assert((result1 as Result.Success).data.id == appId1)
        assert((result2 as Result.Success).data.id == appId2)
        verify(repository, times(1)).getAppById(appId1)
        verify(repository, times(1)).getAppById(appId2)
    }
}