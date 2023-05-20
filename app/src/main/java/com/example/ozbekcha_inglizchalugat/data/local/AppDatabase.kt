package com.example.ozbekcha_inglizchalugat.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ozbekcha_inglizchalugat.data.models.DictionaryModel
import com.example.ozbekcha_inglizchalugat.data.models.FavouritesModel

@Database(
    entities = [DictionaryModel::class, FavouritesModel::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao

}