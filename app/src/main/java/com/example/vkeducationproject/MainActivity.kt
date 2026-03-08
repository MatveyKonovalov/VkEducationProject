package com.example.vkeducationproject

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.vkeducationproject.ui.theme.VkEducationProjectTheme
import coil.compose.AsyncImage
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.LineHeightStyle
import kotlin.io.path.Path
import kotlin.io.path.moveTo

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            VkEducationProjectTheme {

                MainDisplay()
            }
        }
    }
}
@Composable
fun MainDisplay(){
    // Тестовые данные для отображения
    val data = makeData(
        titles = titles,
        descriptions = descriptions,
        categories = categories,
        icons = urls )
    Column{
        ShowTitleRuStore()
        ShowScrollAppsColumn(data)
    }

}
@Composable
fun ShowScrollAppsColumn(data: List<AppString>){
    // Делаю изгиб с помощью двух Box
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.rustoreTitleColor)) // Цвет рустора
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 30.dp)
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(Color.White)
        ) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White))
            {
                items(data){app ->
                    app.ShowAppPage()
                }
            }
        }

    }

}
@Composable
fun ShowTitleRuStore(){
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp)
        .background(colorResource(R.color.rustoreTitleColor)))
    {
        Row(Modifier.align(Alignment.CenterStart),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center){
            Icon(
                imageVector = Icons.Filled.Menu,
                modifier = Modifier.padding(10.dp),
                contentDescription = "Избранное", // Для доступности (accessibility)
                tint = Color.White // Цвет иконки
            )

            Text("RuStore",
                color = Color.White,
                modifier = Modifier.padding(10.dp),
                fontSize = 28.sp)

            Spacer(Modifier.weight(1f))

            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = "Дополнительная информация",
                tint = Color.White,
                modifier = Modifier.padding(end=10.dp)
            )
            }

    }
}
@Composable
fun makeData(titles: List<String>,
             descriptions: List<String>,
             icons: List<String>,
             categories: List<Category>): List<AppString>
{
    return titles.mapIndexed { index, string ->
        AppString(
            title=string,
            iconUrl = icons[index],
            description = descriptions[index],
            category=categories[index]) }.toList()
}





