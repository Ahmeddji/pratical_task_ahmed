package com.logger.practicletask.binding

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.internal.CollapsingTextHelper
import com.google.android.material.textfield.TextInputLayout
import com.logger.practicletask.R

@SuppressLint("RestrictedApi")
@BindingAdapter("setFontFamily")
fun TextInputLayout.setFontFamily(context: Context) {
    val font = ResourcesCompat.getFont(context, R.font.roboto)
    try {
        this.typeface = font
        val collapsingTextHelperField =
            this::class.java.getDeclaredField("collapsingTextHelper").apply {
                isAccessible = true
            }
        val collapsingTextHelper = collapsingTextHelperField.get(this) as CollapsingTextHelper

        collapsingTextHelper.collapsedTypeface = font
    } catch (e: Exception) {
    }
}

fun TextInputLayout.setErrorWithMessage(errorMessage: String?) {
    isErrorEnabled = errorMessage != null
    error = errorMessage
}