package com.logger.practicletask.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.logger.practicletask.db.entities.UserDataModel
import com.logger.practicletask.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel
@Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    var mGender = -1
    val registerUiModel = RegisterUIModel()

    private val _registrationResultLiveData = MutableLiveData<RegistrationResult?>()
    val registrationResultLiveData: LiveData<RegistrationResult?>
        get() = _registrationResultLiveData

    fun setGenderSelection(gender: Int) {
        mGender = gender
    }

    fun onSignUp() {
        viewModelScope.launch {
            if (userRepository.getUserByEmail(registerUiModel.email.email) == null) {
                if (userRepository.getUserByPhoneNumber(registerUiModel.phoneNumber) == null) {
                    doRegistration()
                    val userDataModel = userRepository.getUserByEmail(registerUiModel.email.email)
                    val userId = userDataModel?.id
                    if (userId != null) {
                        _registrationResultLiveData.value = RegistrationResult.RegistrationSuccessful(userId)
                    } else {
                        _registrationResultLiveData.value = RegistrationResult.RegistrationFailed
                    }
                } else {
                    _registrationResultLiveData.value = RegistrationResult.PhoneNumberExist
                }
            } else {
                _registrationResultLiveData.value = RegistrationResult.EmailExist
            }
        }
    }

    private suspend fun doRegistration() {
        userRepository.addUser(
            UserDataModel(
                fullName = registerUiModel.fullName,
                email = registerUiModel.email.email,
                phoneNumber = registerUiModel.phoneNumber?.toLong(),
                city = registerUiModel.city,
                gender = mGender,
                password = registerUiModel.password.password
            ))
    }

    fun registrationResultHandled() {
        _registrationResultLiveData.value = null
    }
}

sealed class RegistrationResult {
    object EmailExist: RegistrationResult()
    object PhoneNumberExist: RegistrationResult()
    data class RegistrationSuccessful(val userId: Long): RegistrationResult()
    object RegistrationFailed: RegistrationResult()
}