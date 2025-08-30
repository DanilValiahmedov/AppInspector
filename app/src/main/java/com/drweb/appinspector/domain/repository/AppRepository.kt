package com.drweb.appinspector.domain.repository

import com.drweb.appinspector.domain.model.AppDetails
import com.drweb.appinspector.domain.model.AppInfo

interface AppRepository {
    suspend fun getInstalledApps(): List<AppInfo>
    suspend fun getAppDetails(packageName: String): AppDetails
}