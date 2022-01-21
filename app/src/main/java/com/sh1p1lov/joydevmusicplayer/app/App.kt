package com.sh1p1lov.joydevmusicplayer.app

import android.app.Application
import com.sh1p1lov.joydevmusicplayer.di.appModule
import com.sh1p1lov.joydevmusicplayer.di.dataModule
import com.sh1p1lov.joydevmusicplayer.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, domainModule, dataModule))
        }
    }
}