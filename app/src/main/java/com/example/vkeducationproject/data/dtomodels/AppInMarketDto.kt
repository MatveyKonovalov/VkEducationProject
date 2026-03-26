package com.example.vkeducationproject.data.dtomodels

import com.google.gson.annotations.SerializedName

data class AppInMarketDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("category")
    val category: String,

    @SerializedName("iconUrl")
    val iconUrl: String,

    @SerializedName("description")
    val description: String
)