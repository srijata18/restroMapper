package com.example.restro.di.module

import com.example.restro.networkClasses.UseCaseHandler
import com.example.restro.networkClasses.retrofit.RetrofitClient
import com.example.restro.networkClasses.retrofit.RetrofitServiceAnnotator
import com.example.restro.dashboard.dataRepositories.DashboardDataRepository
import com.example.restro.dashboard.dataRepositories.DashboardLocalRepository
import com.example.restro.dashboard.dataRepositories.DashboardRemoteRepository
import com.example.restro.dashboard.useCase.RestroUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    internal fun provideUseCaseHandler() = UseCaseHandler.getInstance()

    @Provides
    @Singleton
    internal fun provideRetrofitServiceAnnotator(): RetrofitServiceAnnotator = RetrofitClient.create()

    @Provides
    internal fun provideUseCase(dataRepository: DashboardDataRepository): RestroUseCase =
        RestroUseCase(dataRepository)

    @Provides
    internal fun provideRemoteData(retrofitServiceAnnotator: RetrofitServiceAnnotator) : DashboardRemoteRepository =
        DashboardRemoteRepository(
            retrofitServiceAnnotator
        )

    @Provides
    internal fun provideLocalData() : DashboardLocalRepository =
        DashboardLocalRepository()

    @Provides
    internal fun provideDataRepository(remoteRepository: DashboardRemoteRepository,
                                       localRepository: DashboardLocalRepository
    ) : DashboardDataRepository =
        DashboardDataRepository(
            remoteRepository,
            localRepository
        )

}