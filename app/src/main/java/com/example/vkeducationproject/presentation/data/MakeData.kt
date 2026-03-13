package com.example.vkeducationproject.presentation.data

import com.example.vkeducationproject.presentation.models.App



object MakeTestData {
    fun makeData(): List<App>
    {
        return titles.mapIndexed { index, string -> App(
            name=string,
            iconUrl = urls[index],
            description = descriptions[index],
            category=categories[index],
            developer = companies[index],
            ageRating = ageRatings[index],
            size = sizes[index], id="$index")}.toList()
    }
}