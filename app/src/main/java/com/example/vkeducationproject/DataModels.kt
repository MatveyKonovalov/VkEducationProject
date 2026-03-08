package com.example.vkeducationproject

import androidx.annotation.StringRes

enum class Category(@StringRes val categoryId: Int){
    FINANCE(R.string.category_name_finance),
    TOOLS(R.string.category_name_tools),
    GAME(R.string.category_name_game),
    TRANSPORT(R.string.category_name_transport)
}


