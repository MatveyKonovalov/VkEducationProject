package com.example.vkeducationproject.domain.usecases

import com.example.vkeducationproject.domain.AppRepository
import com.example.vkeducationproject.domain.models.App
import com.example.vkeducationproject.domain.models.AppInMarket
import javax.inject.Inject

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
    object Loading : Result<Nothing>()
}

class GetAppsUseCase @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(): Result<List<AppInMarket>> {
        return try {
            val apps = repository.getApps()
            Result.Success(apps)
        } catch (e: Exception) {
            Result.Error(e.message ?: "Unknown error")
        }
    }
}

class GetAppByIdUseCase @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(id: String): Result<App> {
        return try {
            val app = repository.getAppById(id)
            Result.Success(app)
        } catch (e: Exception) {
            Result.Error(e.message ?: "Failed to load app")
        }
    }
}

class ToggleWishlistUseCase @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(id: String): Result<Unit> {
        return try {
            repository.toggleWishlist(id)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e.message ?: "Failed to update wishlist")
        }
    }
}

class OnLogoClickUseCase @Inject constructor() {
    operator fun invoke(): String {
        return "Нажатие на логотип"
    }
}