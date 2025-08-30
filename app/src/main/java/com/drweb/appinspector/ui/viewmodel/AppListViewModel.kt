package com.drweb.appinspector.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drweb.appinspector.domain.usecase.GetInstalledAppsUseCase
import com.drweb.appinspector.ui.model.AppListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppListViewModel(
    private val getInstalledAppsUseCase: GetInstalledAppsUseCase,
) : ViewModel() {

    private val _apps = MutableStateFlow(AppListState())
    val apps: StateFlow<AppListState> = _apps

    init {
        viewModelScope.launch {
            val installedApps = getInstalledAppsUseCase()

            _apps.update {
                it.copy(
                    isLoading = false,
                    apps = installedApps,
                )
            }
        }
    }


}