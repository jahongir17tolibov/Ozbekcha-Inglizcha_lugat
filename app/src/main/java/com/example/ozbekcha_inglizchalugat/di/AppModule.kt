package com.example.ozbekcha_inglizchalugat.di

import androidx.room.Room
import com.example.ozbekcha_inglizchalugat.data.local.AppDatabase
import com.example.ozbekcha_inglizchalugat.data.source.LocalDataSource
import com.example.ozbekcha_inglizchalugat.data.prefs.DataStoreManager
import com.example.ozbekcha_inglizchalugat.domain.repo.MainRepository
import com.example.ozbekcha_inglizchalugat.domain.repo.MainRepositoryImpl
import com.example.ozbekcha_inglizchalugat.domain.repo.RoomRepository
import com.example.ozbekcha_inglizchalugat.domain.repo.RoomRepositoryImpl
import com.example.ozbekcha_inglizchalugat.presentation.viewmodels.DictionaryDataViewModel
import com.example.ozbekcha_inglizchalugat.presentation.viewmodels.FavouritesViewModel
import com.example.ozbekcha_inglizchalugat.presentation.viewmodels.ThemeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        Room.databaseBuilder(
            androidContext(), AppDatabase::class.java, "DictionaryApp.db"
        ).build()
    }

    single { get<AppDatabase>().appDao() }

    single { DataStoreManager(androidContext()) }

    single { LocalDataSource(androidContext()) }

    factory<MainRepository> { MainRepositoryImpl(get(), get()) }

    factory<RoomRepository> { RoomRepositoryImpl(get()) }

}

val viewModelModule = module {

    viewModel {
        ThemeViewModel(get())
    }

    viewModel {
        DictionaryDataViewModel(get(), get())
    }

    viewModel {
        FavouritesViewModel(get())
    }

}