package com.example.vkeducationproject.data.models

import androidx.annotation.StringRes
import com.example.vkeducationproject.R

enum class Category(@StringRes val categoryId: Int){
    FINANCE(R.string.category_name_finance),
    TOOLS(R.string.category_name_tools),
    GAME(R.string.category_name_game),
    TRANSPORT(R.string.category_name_transport)
}


