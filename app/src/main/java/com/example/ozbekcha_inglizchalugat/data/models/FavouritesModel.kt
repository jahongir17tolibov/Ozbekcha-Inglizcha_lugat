package com.example.ozbekcha_inglizchalugat.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ozbekcha_inglizchalugat.utils.Constants.FAV_TABLE_NAME
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Entity(tableName = FAV_TABLE_NAME)
@Parcelize
data class FavouritesModel(
    val id: String,
    val english: String,
    val transcript: String,
    val uzbek: String,
    val isFavourite: Boolean = false
) : Parcelable {

    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    var favID: Int? = null

}
