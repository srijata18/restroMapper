package com.example.restro.di.component

import com.example.restro.dashboard.views.DashBoardActivity
import com.example.restro.dashboard.di.DashboardActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [DashboardActivityModule::class])
    internal abstract fun bindMainActivity(): DashBoardActivity


}