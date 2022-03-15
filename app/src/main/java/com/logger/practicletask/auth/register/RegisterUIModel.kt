package com.logger.practicletask.auth.register

import android.content.Context
import com.logger.practicletask.R
import com.logger.practicletask.common.ValidationUtils
import com.logger.practicletask.db.entities.UserDataModel
import com.logger.practicletask.models.ui.ConfirmPasswordModel
import com.logger.practicletask.models.ui.EmailModel
import com.logger.practicletask.models.ui.PasswordModel

data class RegisterUIModel(
    var fullName: String? = null,
    var email: EmailModel = EmailModel(),
    var phoneNumber: String? = null,
    var city: String? = null,
    var gender: String? = null,
    var password: PasswordModel = PasswordModel(),
    var confirmPassword: ConfirmPasswordModel = ConfirmPasswordModel()
) {

    var fullNameError: String? = null
    var phoneError: String? = null
    var cityError: String? = null
    var genderError: String? = null

    fun validateRegistration(context: Context): Boolean {
        var isValidRegister = true

        if (!isFullNameValid(context)) {
            isValidRegister = false
        }

        if (!email.isEmailValid(context)) {
            isValidRegister = false
        }

        if (!isPhoneNumberValid(context)) {
            isValidRegister = false
        }

        if (!isCityValid(context)) {
            isValidRegister = false
        }

        if (!isGenderValid(context)) {
            isValidRegister = false
        }

        if (!password.isPasswordValidWithContent(context)) {
            isValidRegister = false
        }

        if (!confirmPassword.isConfirmPasswordValid(context, password.password)) {
            isValidRegister = false
        }

        return isValidRegister
    }

    private fun isFullNameValid(context: Context): Boolean {
        return if (ValidationUtils.isValidData(fullName ?: "")) {
            fullNameError = null
            true
        } else {
            fullNameError = context.getString(R.string.please_enter_full_name)
            false
        }
    }

    private fun isPhoneNumberValid(context: Context): Boolean {
        return if (ValidationUtils.isValidData(phoneNumber ?: "")) {
            phoneError = null
            true
        } else {
            phoneError = context.getString(R.string.please_enter_phone_number)
            false
        }
    }

    private fun isCityValid(context: Context): Boolean {
        return if (ValidationUtils.isValidData(city ?: "")) {
            cityError = null
            true
        } else {
            cityError = context.getString(R.string.please_enter_city)
            false
        }
    }

    private fun isGenderValid(context: Context): Boolean {
        return if (ValidationUtils.isValidData(gender ?: "")) {
            genderError = null
            true
        } else {
            genderError = context.getString(R.string.please_enter_gender)
            false
        }
    }
}