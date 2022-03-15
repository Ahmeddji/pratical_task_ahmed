package com.logger.practicletask.models.ui

import android.content.Context
import com.logger.practicletask.R
import com.logger.practicletask.common.ValidationUtils

data class EmailModel(
    var email: String? = null
) {
    var emailError: String? = null

    fun isEmailValid(context: Context): Boolean {
        return if (ValidationUtils.isValidEmail(email ?: "")) {
            emailError = null
            true
        } else {
            emailError = context.getString(R.string.please_enter_valid_email_address)
            false
        }
    }
}
