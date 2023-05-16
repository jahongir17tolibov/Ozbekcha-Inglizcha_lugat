package com.example.ozbekcha_inglizchalugat.di

import android.app.Application
import android.content.Context
import com.example.ozbekcha_inglizchalugat.presentation.viewmodels.ThemeViewModel
import com.example.ozbekcha_inglizchalugat.utils.DataStoreManager
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { provideDataStore(androidContext()) }

}

private fun provideDataStore(context: Context): DataStoreManager = DataStoreManager(context)

val viewModelModule = module {

    viewModel {
        ThemeViewModel(get())
    }

}