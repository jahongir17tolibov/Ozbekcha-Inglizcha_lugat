package com.example.ozbekcha_inglizchalugat.utils

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

object BaseUtils {

    fun Fragment.showSnackToast(message: String?, duration: Int = Toast.LENGTH_SHORT) {
        Snackbar.make(requireView(), message!!, duration).show()
    }

    fun Fragment.slideLeftAnim(dur: Long? = null): Animation =
        TranslateAnimation(100f, 0f, 0f, 0f).apply {
            duration = dur ?: 100
        }

}