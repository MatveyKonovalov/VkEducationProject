package com.example.vkeducationproject.presentation.apppage

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.vkeducationproject.presentation.viewmodels.AppViewModel

@Composable
fun AppDetailsScreen(
    modifier: Modifier = Modifier,
    id: String,
    viewModel: AppViewModel,
    onBack: () -> Unit = {}
) {

    LaunchedEffect(id) {
        viewModel.loadAppDetails(id)
    }


    // Наблюдение за состояниями
    val displayApp by viewModel.currentApp.collectAsStateWithLifecycle()
    val descriptionCollapsed by viewModel.showDescription.collectAsStateWithLifecycle()
    val toastMessage by viewModel.showToast.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(toastMessage) {
        toastMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            viewModel.onToastShown()
        }
    }

    Column(modifier) {
        Toolbar(
            onBackClick = onBack,
            onShareClick = {
                viewModel.onShareClick()
            },
        )

        Spacer(Modifier.height(8.dp))

        AppDetailsHeader(
            app = displayApp,
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        Spacer(Modifier.height(16.dp))

        InstallButton(
            onClick = {
                viewModel.onInstallClick()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(Modifier.height(12.dp))

        ScreenshotsList(
            screenshotUrlList = displayApp.screenshotUrlList,
            contentPadding = PaddingValues(horizontal = 16.dp),
        )

        Spacer(Modifier.height(12.dp))

        AppDescription(
            description = displayApp.description,
            collapsed = descriptionCollapsed,
            onReadMoreClick = {
                viewModel.changeDescriptionStatus()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )

        Spacer(Modifier.height(12.dp))

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            color = MaterialTheme.colorScheme.outlineVariant,
        )

        Spacer(Modifier.height(12.dp))

        Developer(
            name = displayApp.developer,
            onClick = {
                viewModel.onDeveloperClick()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
        )
    }
}