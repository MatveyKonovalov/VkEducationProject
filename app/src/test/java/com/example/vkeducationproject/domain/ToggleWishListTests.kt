package com.example.vkeducationproject.domain.usecases

import com.example.vkeducationproject.domain.AppRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class ToggleWishlistUseCaseTest {

    @Mock
    private lateinit var repository: AppRepository

    private lateinit var toggleWishlistUseCase: ToggleWishlistUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        toggleWishlistUseCase = ToggleWishlistUseCase(repository)
    }

    @Test
    fun `invoke should return Success when repository successfully toggles wishlist`() = runTest {
        // Given
        val appId = "123"
        // Просто мокаем без doNothing
        `when`(repository.toggleWishlist(appId)).thenReturn(Unit)

        // When
        val result = toggleWishlistUseCase(appId)

        // Then
        assert(result is Result.Success)
        verify(repository, times(1)).toggleWishlist(appId)
    }

    @Test
    fun `invoke should return Error when repository throws exception`() = runTest {
        // Given
        val appId = "123"
        val exception = RuntimeException("Database error")
        `when`(repository.toggleWishlist(appId)).thenThrow(exception)

        // When
        val result = toggleWishlistUseCase(appId)

        // Then
        assert(result is Result.Error)
        assert((result as Result.Error).message == "Database error")
        verify(repository, times(1)).toggleWishlist(appId)
    }

    @Test
    fun `invoke should return Error with default message when exception has null message`() = runTest {
        // Given
        val appId = "123"
        val exception = RuntimeException()
        `when`(repository.toggleWishlist(appId)).thenThrow(exception)

        // When
        val result = toggleWishlistUseCase(appId)

        // Then
        assert(result is Result.Error)
        assert((result as Result.Error).message == "Failed to update wishlist")
        verify(repository, times(1)).toggleWishlist(appId)
    }

    @Test
    fun `invoke should handle multiple toggles correctly`() = runTest {
        // Given
        val appId = "123"
        `when`(repository.toggleWishlist(appId)).thenReturn(Unit, Unit)

        // When
        val result1 = toggleWishlistUseCase(appId)
        val result2 = toggleWishlistUseCase(appId)

        // Then
        assert(result1 is Result.Success)
        assert(result2 is Result.Success)
        verify(repository, times(2)).toggleWishlist(appId)
    }
}