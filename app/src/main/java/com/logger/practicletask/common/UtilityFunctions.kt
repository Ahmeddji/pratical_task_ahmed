package com.logger.practicletask.common

import android.app.Activity
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import com.google.android.material.snackbar.Snackbar
import com.logger.practicletask.R

object UtilityMethods {

    private fun showSnackbar(context: Activity, errorMessage: String, snackbarType: Int) {
        try {
            val snackbar =
                Snackbar.make(
                    context.findViewById(android.R.id.content),
                    errorMessage,
                    Snackbar.LENGTH_LONG
                )
            val snackBarView = snackbar.view
            if (snackbarType == SnackBarTypes.SNACKBAR_ERROR)
                snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.red_EA4335))
            else if (snackbarType == SnackBarTypes.SNACKBAR_SUCCESS)
                snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.green))
            val textView = snackBarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            TextViewCompat.setTextAppearance(textView, R.style.snackbarTextView)
            snackbar.show()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    fun showPositiveSnackbar(context: Activity, errorMessage: String) {
        showSnackbar(context, errorMessage, SnackBarTypes.SNACKBAR_SUCCESS)
    }

    fun showNagativeSnackbar(context: Activity, errorMessage: String) {
        showSnackbar(context, errorMessage, SnackBarTypes.SNACKBAR_ERROR)
    }
}

object SnackBarTypes {
    const val SNACKBAR_SUCCESS = 1
    const val SNACKBAR_ERROR = 2
}