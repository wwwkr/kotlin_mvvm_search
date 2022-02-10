package com.example.kotlin_mvvm_search

import android.app.Application
import org.koin.android.ext.android.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(applicationContext, myDiModule)
    }
}