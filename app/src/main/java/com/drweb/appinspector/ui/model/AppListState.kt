package com.drweb.appinspector.ui.model

import com.drweb.appinspector.domain.model.AppInfo

data class AppListState(
    val isLoading: Boolean = true,
    val apps: List<AppInfo> = emptyList(),
)