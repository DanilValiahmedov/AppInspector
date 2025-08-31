package com.drweb.appinspector.domain.usecase

import com.drweb.appinspector.domain.model.AppInfo
import com.drweb.appinspector.domain.repository.AppRepository

class GetInstalledAppsUseCase(private val repository: AppRepository) {
    suspend operator fun invoke(): Result<List<AppInfo>> = repository.getInstalledApps()
}