package com.example.ozbekcha_inglizchalugat.data.source

import android.content.Context
import com.example.ozbekcha_inglizchalugat.data.models.DictionaryModel
import com.example.ozbekcha_inglizchalugat.data.models.MainModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LocalDataSource(private val context: Context) {

    fun getItemsFromJson(): List<DictionaryModel> {
        val json = context.assets.open("dictionary.json").bufferedReader().use { it.readText() }
        return parseItemsJson(json)
    }

    private fun parseItemsJson(json: String): List<DictionaryModel> {
        val typeToken = object : TypeToken<List<DictionaryModel>>() {}.type
        return Gson().fromJson(json, typeToken)
    }

}