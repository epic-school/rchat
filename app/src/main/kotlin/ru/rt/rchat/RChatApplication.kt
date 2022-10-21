package ru.rt.rchat

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.rt.rchat.di.appModule
import ru.rt.rchat.di.dispatchers
import ru.rt.rchat.di.firebaseModule
import ru.rt.rchat.features.allusers.allUsersModule
import ru.rt.rchat.features.autorization.authModule

class RChatApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@RChatApplication)
            modules(appModule, firebaseModule, dispatchers, allUsersModule, authModule)
        }
    }
}