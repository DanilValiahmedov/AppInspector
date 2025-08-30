package com.drweb.appinspector.data.repository

import android.content.Context
import android.content.pm.PackageManager
import com.drweb.appinspector.domain.model.AppDetails
import com.drweb.appinspector.domain.model.AppInfo
import com.drweb.appinspector.domain.repository.AppRepository
import com.drweb.appinspector.utils.CheckSum
import java.io.File

class AppRepositoryImpl(
    private val context: Context,
) : AppRepository {
    private val packageManager = context.packageManager

    override suspend fun getInstalledApps(): List<AppInfo> {
        return packageManager.getInstalledApplications(PackageManager.GET_META_DATA).map {
            AppInfo(
                appName = packageManager.getApplicationLabel(it).toString(),
                packageName = it.packageName,
                icon = packageManager.getApplicationIcon(it)
            )
        }
    }

    override suspend fun getAppDetails(packageName: String): AppDetails {
        val info = packageManager.getPackageInfo(packageName, 0)
        val appInfo = packageManager.getApplicationInfo(packageName, 0)
        val apkFile = File(appInfo.sourceDir)

        return AppDetails(
            appName = packageManager.getApplicationLabel(appInfo).toString(),
            packageName = packageName,
            versionName = info.versionName ?: "",
            checksum = CheckSum.calculateChecksum(apkFile),
        )
    }
}