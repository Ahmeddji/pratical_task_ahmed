package com.logger.practicletask.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.logger.practicletask.auth.register.RegisterUIModel
import com.logger.practicletask.common.Gender
import com.logger.practicletask.db.entities.UserDataModel
import com.logger.practicletask.models.ui.ConfirmPasswordModel
import com.logger.practicletask.models.ui.EmailModel
import com.logger.practicletask.models.ui.PasswordModel
import com.logger.practicletask.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
@Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    var mGender = -1
    var mUserDataModel = UserDataModel()
    var registerUiModel = RegisterUIModel()

    private val _profileDataSetLiveData = MutableLiveData<ProfileResult?>()
    val profileDataSetLiveData: LiveData<ProfileResult?>
        get() = _profileDataSetLiveData

    fun processUserId(userId: String?) {
        userId?.let {
            viewModelScope.launch {
                userRepository.getUser(userId.toLong())?.let {
                    mUserDataModel = it
                }

                registerUiModel = RegisterUIModel(
                    mUserDataModel.fullName,
                    EmailModel(mUserDataModel.email),
                    mUserDataModel.phoneNumber?.toString(),
                    mUserDataModel.city,
                    getGender(mUserDataModel.gender ?: 0),
                    PasswordModel(mUserDataModel.password),
                    ConfirmPasswordModel(mUserDataModel.password)
                )

                _profileDataSetLiveData.value = ProfileResult.ProfileDataSet(registerUiModel)
            }
        }
    }

    private fun getGender(gender: Int): String {
        mGender = gender
        return if (mGender == Gender.MALE) {
            "Male"
        } else {
            "Female"
        }
    }

    fun setGenderSelection(gender: Int) {
        mGender = gender
    }

    fun updateProfile() {
        val userDataModel = UserDataModel(
            id = mUserDataModel.id,
            fullName = registerUiModel.fullName,
            email = registerUiModel.email.email,
            phoneNumber = registerUiModel.phoneNumber?.toLong(),
            city = registerUiModel.city,
            gender = mGender,
            password = registerUiModel.password.password
        )

        viewModelScope.launch {
            userRepository.updateUser(userDataModel)
            _profileDataSetLiveData.value = ProfileResult.ProfileUpdated
        }
    }

    fun profileResultHandled() {
        _profileDataSetLiveData.value = null
    }
}

sealed class ProfileResult {
    data class ProfileDataSet(val registerUIModel: RegisterUIModel): ProfileResult()
    object ProfileUpdated: ProfileResult()
}

