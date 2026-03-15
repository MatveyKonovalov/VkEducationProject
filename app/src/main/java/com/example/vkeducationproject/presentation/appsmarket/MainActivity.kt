package com.example.vkeducationproject.presentation.appsmarket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.vkeducationproject.R
import com.example.vkeducationproject.presentation.navigation.AppNavigation
import com.example.vkeducationproject.domain.models.App
import com.example.vkeducationproject.presentation.viewmodels.AppViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {

            AppNavigation().NavigationGraph()

        }
    }
}
@Composable
fun MainDisplay(navController: NavHostController,
                viewModel: AppViewModel
){
    LaunchedEffect(Unit) {
        viewModel.loadApps()
    }
    // Тестовые данные для отображения
    val data by viewModel.apps.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackbar.collect{message ->
            snackbarHostState.showSnackbar(message)
        }
    }
    // Отрисовка главного экрана
    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState)}){
        paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(bottom = paddingValues.calculateBottomPadding())){
            ShowTitleRuStore(
                onClickLogo = {viewModel.onLogoClick()}
            )
            ShowScrollAppsColumn(data, navController)
        }
    }


}

@Composable
fun ShowScrollAppsColumn(data: List<App>, navController: NavHostController){
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
                        navController = navController
                    )
                }
            }
        }

    }

}
@Composable
fun ShowTitleRuStore(onClickLogo: () -> Unit){
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

            Text(
                stringResource(R.string.rustore),
                color = Color.White,
                modifier = Modifier.padding(10.dp).clickable{onClickLogo()},
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





