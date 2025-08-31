package com.drweb.appinspector.ui.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drweb.appinspector.ui.viewmodel.AppListViewModel
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppListScreen(
    onAppClick: (String) -> Unit
) {
    val context = LocalContext.current
    val viewModel: AppListViewModel = koinViewModel()
    val appsState by viewModel.appsState.collectAsState()

    LaunchedEffect(appsState.errorMessage) {
        if (appsState.errorMessage != null) {
            Toast.makeText(context, appsState.errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {

        if (appsState.isLoading) {
            CircularProgressIndicator()
        } else if (appsState.errorMessage != null) {
            Button(onClick = { viewModel.loadingInstalledApps() }) {
                Text("Получить список повторно")
            }
        } else {
            LazyColumn {
                items(appsState.installedApps) { app ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onAppClick(app.packageName) }
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = rememberDrawablePainter(app.icon),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )

                        Spacer(Modifier.width(8.dp))

                        Text(text = app.appName, fontSize = 16.sp)
                    }
                }
            }
        }
    }

}