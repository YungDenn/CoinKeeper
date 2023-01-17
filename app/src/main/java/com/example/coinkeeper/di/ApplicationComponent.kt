package com.example.coinkeeper.di

import android.app.Application
import com.example.coinkeeper.presentation.AddFragment
import com.example.coinkeeper.presentation.CoinKeeperApp
import com.example.coinkeeper.presentation.FinanceItemFragment
import com.example.coinkeeper.presentation.StatisticsFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject (application: CoinKeeperApp)

    fun inject (fragment: AddFragment)

    fun inject (fragment: FinanceItemFragment)

    fun inject(fragment: StatisticsFragment)

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance application: Application): ApplicationComponent
    }
}