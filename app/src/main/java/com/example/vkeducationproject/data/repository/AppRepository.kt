package com.example.vkeducationproject.data.repository


import android.service.autofill.Dataset
import com.example.vkeducationproject.data.datasources.MakeTestData
import com.example.vkeducationproject.data.mappers.AppMapper
import com.example.vkeducationproject.domain.AppRepository
import com.example.vkeducationproject.domain.models.AgeRatings
import com.example.vkeducationproject.domain.models.App
import com.example.vkeducationproject.domain.models.Category


class AppRepositoryImpl(
    private val mapper: AppMapper,
    private val data: MakeTestData
): AppRepository {
    private val appsRepository = mutableMapOf<String, App>()

    init{
        loadData()
    }
    // Загрузка данных(в реализации кэширует результат)
    private fun loadData(){
        try{
            val dataDto = data.makeData()

            val dataDomain = dataDto.map{appDto->
                mapper.toDomain(appDto)}

            dataDomain.forEach { app ->
                appsRepository[app.id] = app}

            println("Repository now has: ${appsRepository.size} items")

        } catch (e: Exception){
            println("Failed to load apps: ${e.message}")
        }
    }

    override fun getApps(): List<App> {
        return appsRepository.values.toList()
    }

    override fun getAppById(id: String): App{
        return appsRepository[id] ?: getDefaultApp()
    }
    private fun getDefaultApp(): App = App(
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
        id="1",
    )

}