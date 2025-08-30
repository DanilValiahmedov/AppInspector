package com.drweb.appinspector.di

import com.drweb.appinspector.data.repository.AppRepositoryImpl
import com.drweb.appinspector.domain.repository.AppRepository
import com.drweb.appinspector.domain.usecase.GetAppDetailsUseCase
import com.drweb.appinspector.domain.usecase.GetInstalledAppsUseCase
import com.drweb.appinspector.ui.viewmodel.AppDetailsViewModel
import com.drweb.appinspector.ui.viewmodel.AppListViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<AppRepository> { AppRepositoryImpl(context = get()) }

    singleOf(::GetAppDetailsUseCase)
    singleOf(::GetInstalledAppsUseCase)

    viewModel { AppListViewModel(getInstalledAppsUseCase = get()) }
    viewModel { AppDetailsViewModel(getAppDetailsUseCase = get()) }
}