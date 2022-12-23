package com.example.coinkeeper.di

import androidx.lifecycle.ViewModel
import com.example.coinkeeper.presentation.viewmodels.FinanceItemViewModel
import com.example.coinkeeper.presentation.viewmodels.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(FinanceItemViewModel::class)
    fun bindFinanceItemViewModel(viewModel: FinanceItemViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

}