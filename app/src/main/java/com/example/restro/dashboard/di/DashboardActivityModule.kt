package com.example.restro.dashboard.di

import com.example.restro.networkClasses.UseCaseHandler
import com.example.restro.dashboard.useCase.RestroUseCase
import com.example.restro.dashboard.viewModel.DashboardViewModel
import dagger.Module
import dagger.Provides

@Module
class DashboardActivityModule {

    @Provides
    fun provideMainViewModel(useCaseHandler: UseCaseHandler, restroUseCase: RestroUseCase): DashboardViewModel {
        return DashboardViewModel(
            useCaseHandler,
            restroUseCase
        )
    }

}