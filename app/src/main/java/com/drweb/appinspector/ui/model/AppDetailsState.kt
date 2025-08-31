package com.drweb.appinspector.ui.model

import com.drweb.appinspector.domain.model.AppDetails

data class AppDetailsState(
    val isLoading: Boolean = true,
    val details: AppDetails? = null,
    val errorMessage: String? = null,
)