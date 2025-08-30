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

    private val _details = MutableStateFlow(AppDetailsState())
    val details: StateFlow<AppDetailsState> = _details

    fun getAppDetails(packageName: String) {
        viewModelScope.launch {
            val appDetails = getAppDetailsUseCase(packageName)
            _details.update {
                it.copy(
                    isLoading = false,
                    details = appDetails,
                )
            }
        }
    }

}