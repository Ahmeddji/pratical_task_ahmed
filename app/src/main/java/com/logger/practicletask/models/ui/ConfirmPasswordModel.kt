package com.logger.practicletask.models.ui

import android.content.Context
import com.logger.practicletask.R
import com.logger.practicletask.common.ValidationUtils

data class ConfirmPasswordModel(
    var confirmPassword: String? = null
) {
    var confirmPasswordError: String? = null

    fun isConfirmPasswordValid(context: Context, password: String?): Boolean {
        return if (ValidationUtils.isValidConfirmPassword(password ?: "", confirmPassword ?: "")) {
            confirmPasswordError = null
            true
        } else {
            confirmPasswordError = context.getString(R.string.password_didnt_match)
            false
        }
    }
}
