package com.example.ozbekcha_inglizchalugat.utils

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.text.Layout
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AlignmentSpan
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.example.ozbekcha_inglizchalugat.R
import com.example.ozbekcha_inglizchalugat.data.models.DictionaryModel
import com.example.ozbekcha_inglizchalugat.presentation.adapters.DictionaryEngAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

object BaseUtils {

    private lateinit var dialog: MaterialAlertDialogBuilder
    fun copyToClipBoard(text: String?, context: Context) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipDataTextView = ClipData.newPlainText("text", text!!.toString())
        clipboard.setPrimaryClip(clipDataTextView)

        Toast.makeText(context, "Text copied to clipboard", Toast.LENGTH_SHORT).show()
//        tvTextToPaste.text = clipboardManager.primaryClip?.getItemAt(0)?.text -> Paste from clipboard
    }

    fun Fragment.showSnackToast(message: String?, duration: Int = Toast.LENGTH_SHORT) {
        Snackbar.make(requireView(), message!!, duration).show()
    }

    fun Fragment.slideLeftAnim(dur: Long? = null): Animation =
        TranslateAnimation(100f, 0f, 0f, 0f).apply {
            duration = dur ?: 100
        }

    fun capitalizeFirstLetter(word: String): String {
        if (word.isEmpty()) {
            return word
        }
        return word.substring(0, 1).uppercase() + word.substring(1)
    }

    fun initDialogForAdapter(context: Context, words: DictionaryModel) {
        val message = SpannableString("[${words.transcript}] ${words.type} — ${words.uzbek}")
        message.setSpan(
            AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
            0,
            message.length,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        dialog = MaterialAlertDialogBuilder(context, R.style.MyCustomizedDialog)
            .setTitle(words.english)
            .setMessage(message)
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
            }
            .setNeutralButton("Copy") { _, _ ->
                copyToClipBoard(words.english, context)
            }
        dialog.show()
    }

}