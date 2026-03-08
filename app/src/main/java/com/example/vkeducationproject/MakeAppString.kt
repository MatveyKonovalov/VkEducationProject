package com.example.vkeducationproject

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage


enum class Category(@StringRes val categoryId: Int){
    FINANCE(R.string.category_name_finance),
    TOOLS(R.string.category_name_tools),
    GAME(R.string.category_name_game),
    TRANSPORT(R.string.category_name_transport)
}

class AppString(
    val title: String,
    val iconUrl: String,
    val description: String,
    val category: Category
){
    @Composable
    private fun ShowIcon() {
        AsyncImage(
            model = iconUrl, // URL изображения
            contentDescription = "Описание изображения",
            modifier = Modifier.size(70.dp), // Размер
            error = painterResource(R.drawable.ic_error),
            placeholder = painterResource(R.drawable.ic_loading)
        )
    }
    @Composable
    private fun ShowDescription(){
        Column{
            // Вывод названия приложения
            Text(title, fontSize = 19.sp)

            // Вывод описания
            Text(description, fontSize = 16.sp)

            // Вывод категории
            Text(stringResource(category.categoryId), fontSize = 12.sp, color = Color.Gray)
        }
    }
    @Composable
    fun ShowAppPage(){
        Row(horizontalArrangement = Arrangement.spacedBy(3.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .clip(RoundedCornerShape(5.dp))
                .shadow(1.dp, spotColor = Color(0xFFD3D3D3))
                .padding(start=10.dp)){
            ShowIcon()
            Spacer(Modifier.padding(5.dp))
            ShowDescription()
        }
    }

}