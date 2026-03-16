package com.example.vkeducationproject.data.datasources

import com.example.vkeducationproject.data.dtomodels.AppDto




class MakeTestData {
    fun makeData(): List<AppDto>
    {
        return titles.mapIndexed { index, string -> AppDto(
            name=string,
            iconUrl = urls[index],
            description = descriptions[index],
            category= categories[index],
            developer = companies[index],
            ageRating = ageRatings[index],
            size = sizes[index], id="$index")}.toList()
    }
}