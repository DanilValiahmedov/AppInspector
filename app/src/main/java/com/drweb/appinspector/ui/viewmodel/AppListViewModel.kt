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

    private val _appsState = MutableStateFlow(AppListState())
    val appsState: StateFlow<AppListState> = _appsState

    init {
        loadingInstalledApps()
    }

    fun loadingInstalledApps() {
        viewModelScope.launch {
            _appsState.update {
                it.copy(
                    isLoading = false,
                    installedApps = emptyList(),
                    errorMessage = null,
                )
            }

            val result = getInstalledAppsUseCase()

            result
                .onSuccess { apps ->
                    _appsState.update {
                        it.copy(
                            isLoading = false,
                            installedApps = apps,
                            errorMessage = null,
                        )
                    }
                }
                .onFailure { error ->
                    val message = when(error) {
                        is SecurityException -> "Нет прав на доступ к списку приложений"
                        else -> error.localizedMessage ?: "Неизвестная ошибка"
                    }

                    _appsState.update {
                        it.copy(
                            isLoading = false,
                            installedApps = emptyList(),
                            errorMessage = message,
                        )
                    }
                }
        }
    }

}