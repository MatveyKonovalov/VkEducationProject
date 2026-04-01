package com.example.vkeducationproject.data.repository

import com.example.vkeducationproject.data.datasources.MakeTestData
import com.example.vkeducationproject.data.datasources.local.AppDetailsDao
import com.example.vkeducationproject.data.datasources.local.AppDetailsEntityMapper
import com.example.vkeducationproject.data.mappers.AppInMarketMapper
import com.example.vkeducationproject.data.mappers.AppMapper
import com.example.vkeducationproject.domain.AppRepository
import com.example.vkeducationproject.domain.models.AgeRatings
import com.example.vkeducationproject.domain.models.App
import com.example.vkeducationproject.domain.models.AppInMarket
import com.example.vkeducationproject.domain.models.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImpl @Inject constructor(
    private val mapper: AppMapper,
    private val mapperInMarket: AppInMarketMapper,
    private val makeTest: MakeTestData,
    private val dao: AppDetailsDao,
    private val mapperEntity: AppDetailsEntityMapper
) : AppRepository {

    private var appsCache: List<AppInMarket>? = null

    private suspend fun loadData(): List<AppInMarket> {
        return appsCache ?: runCatching {
            val dataDto = makeTest.makeData()
            val dataDomain = dataDto.map { appDto ->
                mapperInMarket.toDomain(appDto)
            }
            appsCache = dataDomain
            dataDomain
        }.getOrElse {
            println("Failed to load apps: ${it.message}")
            emptyList()
        }
    }

    override suspend fun getApps(): List<AppInMarket> {
        return loadData()
    }

    override suspend fun getAppById(id: String): App {
        return try {
            val appInDatabase = dao.getApp(id)

            if (appInDatabase.isNotEmpty()) {
                mapperEntity.toDomain(appInDatabase.first())
            } else {
                val appDto = makeTest.getAppById(id)
                val dataDomain = mapper.toDomain(appDto)

                withContext(Dispatchers.IO) {
                    dao.insert(mapperEntity.toEntity(dataDomain))
                }

                dataDomain
            }
        } catch (exception: Exception) {
            println("Error getting app by id $id: ${exception.message}")
            getDefaultApp()
        }
    }

    override suspend fun toggleWishlist(id: String) {
        val prevStatus = dao.getApp(id).first().isInWishlist
        dao.updateWishlistStatus(id, !prevStatus)
    }

    override fun getDefaultApp(): App = App(
        name = "Гильдия Героев: Экшен ММО РПГ",
        developer = "VK Play",
        category = Category.GAME,
        ageRating = AgeRatings.PEGI12,
        size = 223.7f,
        screenshotUrlList = listOf(
            "https://static.rustore.ru/imgproxy/-y8kd-4B6MQ-1OKbAbnoAIMZAzvoMMG9dSiHMpFaTBc/preset:web_scr_lnd_335/plain/https://static.rustore.ru/apk/393868735/content/SCREENSHOT/dfd33017-e90d-4990-aa8c-6f159d546788.jpg@webp",
            "https://static.rustore.ru/imgproxy/dZCvNtRKKFpzOmGlTxLszUPmwi661IhXynYZGsJQvLw/preset:web_scr_lnd_335/plain/https://static.rustore.ru/apk/393868735/content/SCREENSHOT/60ec4cbc-dcf6-4e69-aa6f-cc2da7de1af6.jpg@webp",
            "https://static.rustore.ru/imgproxy/g5whSI1uNqaL2TUO7TFfM8M63vXpWXNCm2vlX4Ahvc4/preset:web_scr_lnd_335/plain/https://static.rustore.ru/apk/393868735/content/SCREENSHOT/c2dde8bc-c4ab-482a-80a5-2789149f598d.jpg@webp",
            "https://static.rustore.ru/imgproxy/TjeurtC7BczOVJt74XhjGYuQnG1l4rx6zpDqyMb00GY/preset:web_scr_lnd_335/plain/https://static.rustore.ru/apk/393868735/content/SCREENSHOT/08318f76-7a9c-43aa-b4a7-1aa878d00861.jpg@webp",
        ),
        iconUrl = "https://static.rustore.ru/imgproxy/APsbtHxkVa4MZ0DXjnIkSwFQ_KVIcqHK9o3gHY6pvOQ/preset:web_app_icon_62/plain/https://static.rustore.ru/apk/393868735/content/ICON/3f605e3e-f5b3-434c-af4d-77bc5f38820e.png@webp",
        description = "Легендарный рейд героев в Фэнтези РПГ. Станьте героем гильдии и зразите мастера подземелья!",
        id = "1",
    )
}