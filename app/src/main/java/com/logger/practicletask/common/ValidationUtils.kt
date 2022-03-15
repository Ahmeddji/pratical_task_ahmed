package com.logger.practicletask.common

import android.text.TextUtils
import java.util.regex.Matcher
import java.util.regex.Pattern

object ValidationUtils {

    fun isValidEmail(email: String): Boolean {
        return if (TextUtils.isEmpty(email.trim())) {
            false
        } else {
            android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches();
        }
    }

    fun isValidPassword(password: String): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!()])(?=\\S+$).{4,}$"

        val passwordWithoutSpace = password.trim()

        return if (TextUtils.isEmpty(passwordWithoutSpace) || passwordWithoutSpace.length < AppSettings.MINIMUM_LENGTH_PASSWORD) {
            false
        } else {
            pattern = Pattern.compile(PASSWORD_PATTERN)
            matcher = pattern.matcher(password)
            return matcher.matches()
        }
    }

    fun isValidConfirmPassword(password: String, confirmPassword: String): Boolean {
        return if (TextUtils.isEmpty(confirmPassword)) {
            false
        } else {
            confirmPassword == password
        }
    }

    fun isValidName(name: String): Boolean {
        return if (TextUtils.isEmpty(name)) {
            false
        } else {
            name.trim().length >= AppSettings.MINIMUM_LENGTH_NAME
        }
    }

    fun isValidMobileNumber(mobileNumber: String): Boolean {
        return if (TextUtils.isEmpty(mobileNumber)) {
            false
        } else {
            mobileNumber.trim().length == AppSettings.LENGTH_MOBILE
        }
    }

    fun isValidData(value: String): Boolean {
        return !TextUtils.isEmpty(value)
    }
}