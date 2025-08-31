package com.drweb.appinspector.ui.navigation

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.drweb.appinspector.ui.screen.AppDetailScreen
import com.drweb.appinspector.ui.screen.AppListScreen
import kotlinx.serialization.Serializable

@Serializable
object AppList: NavKey

@Serializable
data class AppDetail(
    val packageName: String,
): NavKey

@Composable
fun AppNav() {

    val backStack = rememberNavBackStack(AppList)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = { key ->
            when (key) {
                is AppList -> NavEntry(key) {
                   AppListScreen(
                        onAppClick = {
                            backStack += AppDetail(it)
                        }
                    )
                }
                is AppDetail -> NavEntry(key) {
                    AppDetailScreen(
                        packageName = key.packageName,
                    )
                }
                else -> NavEntry(key) { Text("Unknown route") }
            }
        }
    )

}