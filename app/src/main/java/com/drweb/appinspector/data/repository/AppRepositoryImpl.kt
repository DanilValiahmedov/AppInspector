package com.drweb.appinspector.data.repository

import android.content.Context
import android.content.pm.PackageManager
import com.drweb.appinspector.domain.model.AppDetails
import com.drweb.appinspector.domain.model.AppInfo
import com.drweb.appinspector.domain.repository.AppRepository
import com.drweb.appinspector.utils.CheckSum
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

class AppRepositoryImpl(
    private val context: Context,
) : AppRepository {
    private val packageManager = context.packageManager

    override suspend fun getInstalledApps(): Result<List<AppInfo>> {
        return try {
            val apps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA).map {
                AppInfo(
                    appName = packageManager.getApplicationLabel(it).toString(),
                    packageName = it.packageName,
                    icon = packageManager.getApplicationIcon(it)
                )
            }
            Result.success(apps)
        } catch (e: SecurityException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAppDetails(packageName: String): Result<AppDetails> {
        return try {
            val info = packageManager.getPackageInfo(packageName, 0)
            val appInfo = packageManager.getApplicationInfo(packageName, 0)
            val apkFile = File(appInfo.sourceDir ?: return Result.failure(FileNotFoundException("Файл не найден")))

            val details = AppDetails(
                appName = packageManager.getApplicationLabel(appInfo).toString(),
                packageName = packageName,
                versionName = info.versionName ?: "",
                checksum = CheckSum.calculateChecksum(apkFile),
            )
            Result.success(details)
        } catch (e: PackageManager.NameNotFoundException) {
            Result.failure(e)
        } catch (e: SecurityException) {
            Result.failure(e)
        } catch (e: IOException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}