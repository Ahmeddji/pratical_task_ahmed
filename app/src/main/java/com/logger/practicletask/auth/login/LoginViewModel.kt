package com.logger.practicletask.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.logger.practicletask.auth.register.RegistrationResult
import com.logger.practicletask.db.entities.UserDataModel
import com.logger.practicletask.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    val loginUIModel = LoginUIModel()

    private val _loginResultLiveData = MutableLiveData<LoginResult?>()
    val loginResultLiveData: LiveData<LoginResult?>
        get() = _loginResultLiveData

    fun login() {
        viewModelScope.launch {
            var userDataModel = userRepository.getUserByEmail(loginUIModel.emailOrMobile)
            if (userDataModel == null) {
                userDataModel = userRepository.getUserByPhoneNumber(loginUIModel.emailOrMobile)
                if (userDataModel == null) {
                    _loginResultLiveData.value = LoginResult.UserNotFound
                } else {
                    checkCredentialsAndSetResult(userDataModel)
                }
            } else {
                checkCredentialsAndSetResult(userDataModel)
            }
        }
    }

    private fun checkCredentialsAndSetResult(userDataModel: UserDataModel) {
        if (userDataModel.password?.equals(loginUIModel.password.password) == true) {
            _loginResultLiveData.value = LoginResult.LoginSuccessful(userDataModel.id)
        } else {
            _loginResultLiveData.value = LoginResult.InvalidCredentials
        }
    }

    fun loginResultHandled() {
        _loginResultLiveData.value = null
    }
}

sealed class LoginResult {
    data class LoginSuccessful(val userId: Long): LoginResult()
    object UserNotFound: LoginResult()
    object InvalidCredentials: LoginResult()
}