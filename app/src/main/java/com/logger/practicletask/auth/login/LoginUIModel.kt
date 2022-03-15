package com.logger.practicletask.auth.login

import android.content.Context
import com.logger.practicletask.R
import com.logger.practicletask.common.ValidationUtils
import com.logger.practicletask.models.ui.PasswordModel

data class LoginUIModel(
    var emailOrMobile: String? = null,
    var password: PasswordModel = PasswordModel()
) {

    var emailOrMobileError: String? = null

    fun validateLogin(context: Context): Boolean {
        var isValidLogin = true

        if (!isEmailOrMobileValid(context)) {
            isValidLogin = false
        }

        if (!password.isPasswordValid(context)) {
            isValidLogin = false
        }

        return isValidLogin
    }

    private fun isEmailOrMobileValid(context: Context): Boolean {
        return if (ValidationUtils.isValidData(emailOrMobile ?: "")) {
            emailOrMobileError = null
            true
        } else {
            emailOrMobileError = context.getString(R.string.please_enter_email_or_mobile_number)
            false
        }
    }
}