package com.example.vkeducationproject

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.vkeducationproject.ui.theme.VkEducationProjectTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavHostController
import com.example.vkeducationproject.navigate.AppNavigation
import com.example.vkeducationproject.navigate.AppViewModel
import com.example.vkeducationproject.page.App
import com.example.vkeducationproject.page.AppWithId

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            VkEducationProjectTheme {
                AppNavigation().NavigationGraph()
            }
        }
    }
}
@Composable
fun MainDisplay(navController: NavHostController,
                viewModel: AppViewModel){

    // Тестовые данные для отображения
    val data = makeData(
        titles = titles,
        descriptions = descriptions,
        categories = categories,
        icons = urls,
        companies = companies,
        ageRatings = ageRatings,
        sizes = sizes)

    // Список готовых для работы данных (данные о приложении, его id)
    val appIds = data.map{app -> AppWithId(
        id=viewModel.addInRepository(app),
        appData = app
    )
        }.toList()

    // Отрисовка главного экрана
    Column{
        ShowTitleRuStore()
        ShowScrollAppsColumn(appIds, navController)
    }

}

@Composable
fun ShowScrollAppsColumn(data: List<AppWithId>, navController: NavHostController){
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
                items(data.indices.toList()){appIndex ->
                    ShowAppPage(
                        app = data[appIndex],
                        navController = navController)
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
                contentDescription = "Меню",
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
             categories: List<Category>,
             companies: List<String>,
             ageRatings: List<Int>,
             sizes: List<Float>): List<App>
{
    return titles.mapIndexed { index, string -> App(
            name=string,
            iconUrl = icons[index],
            description = descriptions[index],
            category=categories[index],
            developer = companies[index],
            ageRating = ageRatings[index],
            size = sizes[index])}.toList()
}





