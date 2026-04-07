package com.example.vkeducationproject.domain.usecases

import com.example.vkeducationproject.domain.AppRepository
import com.example.vkeducationproject.domain.models.AppInMarket
import com.example.vkeducationproject.domain.models.Category
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class GetAppsUseCaseTest {

    @Mock
    private lateinit var repository: AppRepository

    private lateinit var getAppsUseCase: GetAppsUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getAppsUseCase = GetAppsUseCase(repository)
    }

    @Test
    fun `invoke should return Success with apps when repository returns data`() = runTest {
        // Given
        val expectedApps = listOf(
            AppInMarket(
                id = "1",
                name = "Test App 1",
                category = Category.GAME,
                iconUrl = "icon_url_1",
                description = "None"
            ),
            AppInMarket(
                id = "2",
                name = "Test App 2",
                category = Category.GAME,
                iconUrl = "icon_url_2",
                description = ""
            )
        )
        `when`(repository.getApps()).thenReturn(expectedApps)

        // When
        val result = getAppsUseCase()

        // Then
        assert(result is Result.Success)
        assert((result as Result.Success).data == expectedApps)
        verify(repository, times(1)).getApps()
    }

    @Test
    fun `invoke should return Error when repository throws exception`() = runTest {
        // Given
        val exception = RuntimeException("Network error")
        `when`(repository.getApps()).thenThrow(exception)

        // When
        val result = getAppsUseCase()

        // Then
        assert(result is Result.Error)
        assert((result as Result.Error).message == "Network error")
        verify(repository, times(1)).getApps()
    }

    @Test
    fun `invoke should return Error with default message when exception has null message`() = runTest {
        // Given
        val exception = RuntimeException()
        `when`(repository.getApps()).thenThrow(exception)

        // When
        val result = getAppsUseCase()

        // Then
        assert(result is Result.Error)
        assert((result as Result.Error).message == "Unknown error")
        verify(repository, times(1)).getApps()
    }

    @Test
    fun `invoke should return Success with empty list when repository returns empty list`() = runTest {
        // Given
        `when`(repository.getApps()).thenReturn(emptyList())

        // When
        val result = getAppsUseCase()

        // Then
        assert(result is Result.Success)
        assert((result as Result.Success).data.isEmpty())
        verify(repository, times(1)).getApps()
    }
}