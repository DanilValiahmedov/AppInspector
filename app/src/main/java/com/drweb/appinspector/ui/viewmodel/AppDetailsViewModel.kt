package com.drweb.appinspector.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drweb.appinspector.domain.usecase.GetAppDetailsUseCase
import com.drweb.appinspector.ui.model.AppDetailsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppDetailsViewModel(
    private val getAppDetailsUseCase: GetAppDetailsUseCase,
) : ViewModel() {

    private val _detailsState = MutableStateFlow(AppDetailsState())
    val detailsState: StateFlow<AppDetailsState> = _detailsState

    fun getAppDetails(packageName: String) {
        viewModelScope.launch {
            val appDetails = getAppDetailsUseCase(packageName)
            _detailsState.update {
                it.copy(
                    isLoading = false,
                    details = appDetails,
                )
            }
        }
    }

}