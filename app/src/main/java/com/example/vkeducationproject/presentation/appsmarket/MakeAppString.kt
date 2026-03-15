package com.example.vkeducationproject.presentation.appsmarket

import androidx.compose.foundation.clickable
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
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.vkeducationproject.R
import com.example.vkeducationproject.domain.models.App


@Composable
private fun ShowIcon(iconUrl: String) {
    AsyncImage(
        model = iconUrl, // URL изображения
        contentDescription = "Описание изображения",
        modifier = Modifier.size(70.dp), // Размер
        error = painterResource(R.drawable.ic_error),
        placeholder = painterResource(R.drawable.ic_loading)
    )
}

@Composable
private fun ShowDescription(app: App){
    Column{
        // Вывод названия приложения
        Text(app.name,
            fontSize = 19.sp)

        // Вывод описания
        Text(app.description,
            fontSize = 16.sp)

        // Вывод категории
        Text(stringResource(
            app.category.categoryId),
            fontSize = 12.sp,
            color = Color.Gray)
    }
}
@Composable
fun ShowAppPage(app: App, navController: NavHostController){
    Row(horizontalArrangement = Arrangement.spacedBy(3.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(5.dp))
            .shadow(1.dp, spotColor = Color(0xFFD3D3D3))
            .padding(start=10.dp)
            .clickable{
                // Индекс нужен для дальнейшей навигации
                navController.navigate(app.getWayForPage())
            }){
        ShowIcon(app.iconUrl)
        Spacer(Modifier.padding(5.dp))
        ShowDescription(app)
    }
}

