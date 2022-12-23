package com.example.coinkeeper.di

import android.app.Application
import com.example.coinkeeper.data.database.AppDatabase
import com.example.coinkeeper.data.database.FinanceListDao
import com.example.coinkeeper.data.repository.FinanceItemListRepositoryImpl
import com.example.coinkeeper.domain.repository.FinanceItemRepository
import dagger.Binds
import dagger.Module
import dagger.Provides


@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun provideFinanceListRepository(impl: FinanceItemListRepositoryImpl): FinanceItemRepository

    companion object{

        @Provides
        @ApplicationScope
        fun provideFinanceListDao(application: Application): FinanceListDao{
            return AppDatabase.getInstance(application).financeListDao()
        }

    }
}