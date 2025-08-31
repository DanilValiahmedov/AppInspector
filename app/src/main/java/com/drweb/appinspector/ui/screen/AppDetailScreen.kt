package com.drweb.appinspector.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drweb.appinspector.ui.viewmodel.AppDetailsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppDetailScreen(
    packageName: String,
) {
    val context = LocalContext.current
    val viewModel: AppDetailsViewModel = koinViewModel()
    val detailsState by viewModel.detailsState.collectAsState()

    LaunchedEffect(packageName) {
        viewModel.getAppDetails(packageName)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {

        if (detailsState.isLoading) {
            CircularProgressIndicator()
        } else if (detailsState.errorMessage != null) {
            Button(onClick = { viewModel.getAppDetails(packageName) }) {
                Text("Повториь попытку")
            }
        } else {
            detailsState.details?.let {
                Column(Modifier.padding(16.dp)) {
                    Text("Название: ${it.appName}", fontSize = 18.sp)
                    Text("Версия: ${it.versionName}")
                    Text("Пакет: ${it.packageName}")
                    Text("Контрольная сумма: ${it.checksum}")

                    Spacer(Modifier.height(16.dp))

                    Button(onClick = {
                        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
                        context.startActivity(intent)
                    }) {
                        Text("Открыть приложение")
                    }
                }
            }
        }
    }
}
