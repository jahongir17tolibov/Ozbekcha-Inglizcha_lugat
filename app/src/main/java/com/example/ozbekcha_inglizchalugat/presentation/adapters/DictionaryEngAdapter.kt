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
import com.example.ozbekcha_inglizchalugat.utils.BaseUtils.initDialogForAdapter

class DictionaryEngAdapter : RecyclerView.Adapter<DictionaryEngAdapter.LettersItemHolder>() {

    var baseList = emptyList<DictionaryModel>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var onItemClickListener: ((DictionaryModel) -> Unit)? = null

    fun setOnStarClickListener(listener: ((DictionaryModel) -> Unit)? = null) {
        onItemClickListener = listener
    }

    inner class LettersItemHolder(private val b: LettersItemLyBinding) :
        RecyclerView.ViewHolder(b.root) {

        @SuppressLint("ResourceAsColor", "NotifyDataSetChanged")
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

                addToFavouritesStar.setOnClickListener {
                    onItemClickListener?.invoke(result)
                    notifyDataSetChanged()
                }

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
            initDialogForAdapter(holder.itemView.context, itemData)
        }

    }

    override fun getItemCount(): Int = baseList.size

}