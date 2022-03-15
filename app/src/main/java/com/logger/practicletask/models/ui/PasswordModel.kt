package com.logger.practicletask.models.ui

import android.content.Context
import com.logger.practicletask.R
import com.logger.practicletask.common.ValidationUtils

data class PasswordModel(
    var password: String? = null
) {
    var passwordError: String? = null

    fun isPasswordValid(context: Context): Boolean {
        return if (ValidationUtils.isValidData(password ?: "")) {
            passwordError = null
            true
        } else {
            passwordError = context.getString(R.string.please_enter_valid_password)
            false
        }
    }

    fun isPasswordValidWithContent(context: Context): Boolean {
        return if (ValidationUtils.isValidPassword(password ?: "")) {
            passwordError = null
            true
        } else {
            passwordError = context.getString(R.string.password_must_be_at_least)
            false
        }
    }
}
