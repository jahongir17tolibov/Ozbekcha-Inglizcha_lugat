package com.example.ozbekcha_inglizchalugat.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.ozbekcha_inglizchalugat.data.models.DictionaryModel
import com.example.ozbekcha_inglizchalugat.data.models.FavouritesModel
import com.example.ozbekcha_inglizchalugat.utils.Constants.CACHE_TABLE_NAME
import com.example.ozbekcha_inglizchalugat.utils.Constants.FAV_TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    /** cached data category **/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWords(data: List<DictionaryModel>)

    @Update
    suspend fun updateDictionary(words: DictionaryModel)

    @Query("SELECT * FROM $CACHE_TABLE_NAME")
    fun getAllWords(): List<DictionaryModel>

    @Query("DELETE FROM $CACHE_TABLE_NAME")
    fun deleteAll()

    @Transaction
    suspend fun deleteAndInsert(data: List<DictionaryModel>) {
        deleteAll()
        insertWords(data)
    }

    /** favourites category **/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavLetters(favouritesModel: FavouritesModel): Long

    @Query("SELECT * FROM $FAV_TABLE_NAME ORDER BY english ASC")
    fun getAllFavWords(): Flow<List<FavouritesModel>>

    @Query("SELECT * FROM $FAV_TABLE_NAME WHERE english = :eng")
    fun getFavWordsName(eng: String?): Flow<FavouritesModel>

    @Query("DELETE FROM $FAV_TABLE_NAME")
    suspend fun deleteAllFavWords()

    @Query("SELECT COUNT(*) FROM $FAV_TABLE_NAME")
    fun getFavDataCount(): Flow<Int>

    @Query("DELETE FROM $FAV_TABLE_NAME WHERE id = :id")
    suspend fun deleteFavWord(id: String?)
    /*******************************************************/

}