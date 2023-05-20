package com.example.ozbekcha_inglizchalugat.presentation.adapters

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.ozbekcha_inglizchalugat.R
import com.example.ozbekcha_inglizchalugat.data.models.DictionaryModel
import com.example.ozbekcha_inglizchalugat.databinding.LettersItemLyBinding

class DictionaryEngAdapter : RecyclerView.Adapter<DictionaryEngAdapter.LettersItemHolder>() {

    var baseList = emptyList<DictionaryModel>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var onItemClickListener: ((DictionaryModel) -> Unit)? = null

    fun setOnItemClickListener(listener: ((DictionaryModel) -> Unit)? = null) {
        onItemClickListener = listener
    }

    inner class LettersItemHolder(val b: LettersItemLyBinding) : RecyclerView.ViewHolder(b.root) {

        @SuppressLint("ResourceAsColor")
        fun bind(result: DictionaryModel) {
            val captLetter = result.english.take(1)
            b.apply {
                lettersInitial.text = captLetter
                letterBoldMain.text = result.english
                letterSecondaryNormal.text = result.uzbek

                val colorId = if (result.isFavourite) R.color.small_items_color
                else R.color.light_dark_color
                val color = ContextCompat.getColor(itemView.context, colorId)
                addToFavouritesStar.imageTintList = ColorStateList.valueOf(color)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LettersItemHolder =
        LettersItemHolder(
            LettersItemLyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: LettersItemHolder, position: Int) {
        val itemData = baseList[position]

        holder.bind(itemData)

        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(itemData)
            notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int = baseList.size

}