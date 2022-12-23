package com.example.coinkeeper.presentation

import android.app.Application
import com.example.coinkeeper.di.DaggerApplicationComponent

class CoinKeeperApp: Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }
}