package com.drweb.appinspector.domain.usecase

import com.drweb.appinspector.domain.model.AppDetails
import com.drweb.appinspector.domain.repository.AppRepository

class GetAppDetailsUseCase(private val repository: AppRepository) {
    suspend operator fun invoke(packageName: String): AppDetails = repository.getAppDetails(packageName)
}