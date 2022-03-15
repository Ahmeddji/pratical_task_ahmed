package com.logger.practicletask.auth.register

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.logger.practicletask.R
import com.logger.practicletask.binding.autoCleared
import com.logger.practicletask.binding.setErrorWithMessage
import com.logger.practicletask.common.Gender
import com.logger.practicletask.common.UtilityMethods
import com.logger.practicletask.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private val viewModel: RegisterViewModel by viewModels()
    private var binding: FragmentRegisterBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = viewModel
        eventListeners()
        dataObservers()
    }

    private fun eventListeners() {
        clickListeners()
        textChangeListener()
    }

    private fun clickListeners() {
        val onClickListener = View.OnClickListener { view ->
            if (view.id == binding.etGender.id) {
                showGenderDialog()

            } else if (view.id == binding.btnSignUp.id) {
                if (viewModel.registerUiModel.validateRegistration(requireActivity())) {
                    viewModel.onSignUp()
                }

                binding.textInputFullName.setErrorWithMessage(viewModel.registerUiModel.fullNameError)
                binding.textInputEmail.setErrorWithMessage(viewModel.registerUiModel.email.emailError)
                binding.textInputMobile.setErrorWithMessage(viewModel.registerUiModel.phoneError)
                binding.textInputCity.setErrorWithMessage(viewModel.registerUiModel.cityError)
                binding.textInputGender.setErrorWithMessage(viewModel.registerUiModel.genderError)
                binding.textInputPassword.setErrorWithMessage(viewModel.registerUiModel.password.passwordError)
                binding.textInputConfirmPassword.setErrorWithMessage(viewModel.registerUiModel.confirmPassword.confirmPasswordError)

            } else if (view.id == binding.tvLogin.id) {
                navigateToLogin()
            }

        }

        binding.etGender.setOnClickListener(onClickListener)
        binding.btnSignUp.setOnClickListener(onClickListener)
        binding.tvLogin.setOnClickListener(onClickListener)
    }

    private fun textChangeListener() {
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(editable: Editable?) {
                if (editable.toString() == binding.etFullName.editableText.toString() && binding.textInputFullName.isErrorEnabled) {
                    binding.textInputFullName.setErrorWithMessage(null)
                } else if (editable.toString() == binding.etEmail.editableText.toString() && binding.textInputEmail.isErrorEnabled) {
                    binding.textInputEmail.setErrorWithMessage(null)
                } else if (editable.toString() == binding.etMobile.editableText.toString() && binding.textInputMobile.isErrorEnabled) {
                    binding.textInputMobile.setErrorWithMessage(null)
                } else if (editable.toString() == binding.etCity.editableText.toString() && binding.textInputCity.isErrorEnabled) {
                    binding.textInputCity.setErrorWithMessage(null)
                } else if (editable.toString() == binding.etGender.editableText.toString() && binding.textInputGender.isErrorEnabled) {
                    binding.textInputGender.setErrorWithMessage(null)
                } else if (editable.toString() == binding.etPassword.editableText.toString() && binding.textInputPassword.isErrorEnabled) {
                    binding.textInputPassword.setErrorWithMessage(null)
                } else if (editable.toString() == binding.etConfirmPassword.editableText.toString() && binding.textInputConfirmPassword.isErrorEnabled) {
                    binding.textInputConfirmPassword.setErrorWithMessage(null)
                }
            }
        }

        binding.etFullName.addTextChangedListener(watcher)
        binding.etEmail.addTextChangedListener(watcher)
        binding.etMobile.addTextChangedListener(watcher)
        binding.etCity.addTextChangedListener(watcher)
        binding.etGender.addTextChangedListener(watcher)
        binding.etPassword.addTextChangedListener(watcher)
        binding.etConfirmPassword.addTextChangedListener(watcher)
    }

    private fun showGenderDialog() {
        val options = resources.getStringArray(R.array.genders)
        val builder = AlertDialog.Builder(context)
        builder.setTitle(getString(R.string.gender))
        builder.setSingleChoiceItems(options, viewModel.mGender) { dialog, which ->
            viewModel.setGenderSelection(which)
            if (viewModel.mGender == Gender.MALE) {
                binding.etGender.setText(R.string.male)
            } else {
                binding.etGender.setText(R.string.female)
            }
            dialog.dismiss()
        }
        builder.create().show()
    }

    private fun dataObservers() {
        viewModel.registrationResultLiveData.observe(viewLifecycleOwner) { result ->
            if (result == null)
                return@observe

            when (result) {
                is RegistrationResult.EmailExist -> {
                    UtilityMethods.showNagativeSnackbar(requireActivity(), getString(R.string.email_already_exist))
                }

                is RegistrationResult.PhoneNumberExist -> {
                    UtilityMethods.showNagativeSnackbar(requireActivity(), getString(R.string.phone_number_already_exist))
                }

                is RegistrationResult.RegistrationSuccessful -> {
                    UtilityMethods.showPositiveSnackbar(requireActivity(), getString(R.string.registration_successful))
                    navigateToLogin()
                }

                is RegistrationResult.RegistrationFailed -> {
                    UtilityMethods.showNagativeSnackbar(requireActivity(), getString(R.string.registration_failed))
                }
            }

            viewModel.registrationResultHandled()
        }
    }

    private fun navigateToLogin() {
        requireActivity().onBackPressed()
    }
}