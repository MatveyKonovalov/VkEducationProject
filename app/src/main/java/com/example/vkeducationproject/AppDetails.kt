package com.example.vkeducationproject

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vkeducationproject.ui.theme.VkEducationProjectTheme

@Composable
fun AppDetails(){
    Column {
        Row(Modifier.fillMaxWidth()){
            // contentDescription - Название картинки
            // tint - Цвет иконки
            // MaterialTheme.colorScheme.surfaceTint адаптирование под разные настройки

            Icon(Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                tint=MaterialTheme.colorScheme.surfaceTint)

            // Растягивание пустого пространства
            Spacer(Modifier.weight(1f))

            Icon(Icons.Default.Share,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.surfaceTint)
        }
    }
}

@Composable
@Preview
fun Preview(){
    // Подкачка цветов
    VkEducationProjectTheme{
        AppDetails()
    }

}