package com.drweb.appinspector.ui.viewmodel

import android.content.pm.PackageManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drweb.appinspector.domain.usecase.GetAppDetailsUseCase
import com.drweb.appinspector.ui.model.AppDetailsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class AppDetailsViewModel(
    private val getAppDetailsUseCase: GetAppDetailsUseCase,
) : ViewModel() {

    private val _detailsState = MutableStateFlow(AppDetailsState())
    val detailsState: StateFlow<AppDetailsState> = _detailsState

    fun getAppDetails(packageName: String) {
        viewModelScope.launch {
            val result = getAppDetailsUseCase(packageName)

            result
                .onSuccess { details ->
                    _detailsState.update {
                        it.copy(
                            isLoading = false,
                            details = details,
                            errorMessage = null
                        )
                    }
                }
                .onFailure { error ->
                    val message = when (error) {
                        is PackageManager.NameNotFoundException -> "Пакет $packageName не найден"
                        is SecurityException -> "Нет прав на доступ к информации о приложении"
                        is IOException -> "Ошибка чтения файла"
                        else -> error.localizedMessage ?: "Неизвестная ошибка"
                    }

                    _detailsState.update {
                        it.copy(
                            isLoading = false,
                            details = null,
                            errorMessage = message
                        )
                    }
                }
        }
    }

}