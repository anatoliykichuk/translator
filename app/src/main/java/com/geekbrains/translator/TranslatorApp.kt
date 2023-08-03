package com.geekbrains.translator

import android.app.Application
import com.geekbrains.translator.di.koin.application
import com.geekbrains.translator.di.koin.mainScreen
import org.koin.core.context.startKoin

class TranslatorApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(listOf(application, mainScreen))
        }
    }
}