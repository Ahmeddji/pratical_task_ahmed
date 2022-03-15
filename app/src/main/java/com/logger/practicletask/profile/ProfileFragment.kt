package com.logger.practicletask.profile

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.logger.practicletask.R
import com.logger.practicletask.auth.AuthActivity
import com.logger.practicletask.binding.autoCleared
import com.logger.practicletask.binding.setErrorWithMessage
import com.logger.practicletask.common.Gender
import com.logger.practicletask.common.SharedPreferencesManager
import com.logger.practicletask.common.UtilityMethods
import com.logger.practicletask.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()
    private var binding: FragmentProfileBinding by autoCleared()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.processUserId(SharedPreferencesManager.getInstance(requireActivity().applicationContext).getUserId())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
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

            } else if (view.id == binding.btnUpdateProfile.id) {
                if (viewModel.registerUiModel.validateRegistration(requireActivity())) {
                    viewModel.updateProfile()
                }

                binding.textInputFullName.setErrorWithMessage(viewModel.registerUiModel.fullNameError)
                binding.textInputEmail.setErrorWithMessage(viewModel.registerUiModel.email.emailError)
                binding.textInputMobile.setErrorWithMessage(viewModel.registerUiModel.phoneError)
                binding.textInputCity.setErrorWithMessage(viewModel.registerUiModel.cityError)
                binding.textInputGender.setErrorWithMessage(viewModel.registerUiModel.genderError)
                binding.textInputPassword.setErrorWithMessage(viewModel.registerUiModel.password.passwordError)
                binding.textInputConfirmPassword.setErrorWithMessage(viewModel.registerUiModel.confirmPassword.confirmPasswordError)

            } else if (view.id == binding.tvLogout.id) {
                SharedPreferencesManager.getInstance(requireActivity().applicationContext).clearPrefs()
                navigateToLogin()
            }

        }

        binding.etGender.setOnClickListener(onClickListener)
        binding.btnUpdateProfile.setOnClickListener(onClickListener)
        binding.tvLogout.setOnClickListener(onClickListener)
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
        viewModel.profileDataSetLiveData.observe(viewLifecycleOwner) {
            if (it == null)
                return@observe

            if (it is ProfileResult.ProfileUpdated) {
                UtilityMethods.showPositiveSnackbar(requireActivity(), getString(R.string.profile_updated_successfully))
            }

            binding.invalidateAll()

            viewModel.profileResultHandled()
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(requireActivity(), AuthActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}