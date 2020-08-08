package com.picpay.desafio.android

import android.app.Application
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PicPayApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            modules(listOf(networkModule, dataModule, viewModule))
        }
    }

}