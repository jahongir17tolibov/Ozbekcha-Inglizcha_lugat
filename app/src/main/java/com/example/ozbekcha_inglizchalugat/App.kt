package com.example.ozbekcha_inglizchalugat

import android.app.Application
import com.example.ozbekcha_inglizchalugat.data.prefs.AppPreference
import com.example.ozbekcha_inglizchalugat.di.appModule
import com.example.ozbekcha_inglizchalugat.di.viewModelModule
import com.example.ozbekcha_inglizchalugat.utils.DataStoreManager
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin

class App : Application(), KoinComponent {

    companion object {
        private lateinit var instance: App
        private lateinit var dataStoreManager: DataStoreManager
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        AppPreference.init(instance)
        dataStoreManager = DataStoreManager(instance)

        startKoin {
            androidLogger()
            androidContext(instance)
            modules(appModule, viewModelModule)
        }

    }

}