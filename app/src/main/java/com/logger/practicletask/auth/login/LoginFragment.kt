package com.logger.practicletask.auth.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.logger.practicletask.R
import com.logger.practicletask.auth.AuthActivity
import com.logger.practicletask.binding.autoCleared
import com.logger.practicletask.binding.setErrorWithMessage
import com.logger.practicletask.common.SharedPreferencesManager
import com.logger.practicletask.common.UtilityMethods
import com.logger.practicletask.databinding.FragmentLoginBinding
import com.logger.practicletask.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()
    private var binding: FragmentLoginBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
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
            if (view.id == binding.btnSignIn.id) {
                if (viewModel.loginUIModel.validateLogin(requireActivity())) {
                    viewModel.login()
                }

                binding.textInputEmail.setErrorWithMessage(viewModel.loginUIModel.emailOrMobileError)
                binding.textInputPassword.setErrorWithMessage(viewModel.loginUIModel.password.passwordError)

            } else if (view.id == binding.tvRegister.id) {
                navigateToRegistration()

            }

        }

        binding.btnSignIn.setOnClickListener(onClickListener)
        binding.tvRegister.setOnClickListener(onClickListener)
    }

    private fun textChangeListener() {
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(editable: Editable?) {
                if (editable.toString() == binding.etEmail.editableText.toString() && binding.textInputEmail.isErrorEnabled) {
                    binding.textInputEmail.setErrorWithMessage(null)
                } else if (editable.toString() == binding.etPassword.editableText.toString() && binding.textInputPassword.isErrorEnabled) {
                    binding.textInputPassword.setErrorWithMessage(null)
                }
            }
        }

        binding.etEmail.addTextChangedListener(watcher)
        binding.etPassword.addTextChangedListener(watcher)
    }

    private fun dataObservers() {
        viewModel.loginResultLiveData.observe(viewLifecycleOwner) { result ->
            if (result == null)
                return@observe

            when (result) {
                is LoginResult.LoginSuccessful -> {
                    SharedPreferencesManager.getInstance(requireActivity().applicationContext).setUserId(result.userId.toString())
                    navigateToHome()
                }

                is LoginResult.UserNotFound -> {
                    UtilityMethods.showNagativeSnackbar(requireActivity(), getString(R.string.user_not_found))
                }

                is LoginResult.InvalidCredentials -> {
                    UtilityMethods.showNagativeSnackbar(requireActivity(), getString(R.string.invalid_credentials))
                }
            }

            viewModel.loginResultHandled()
        }
    }

    private fun navigateToHome() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun navigateToRegistration() {
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
    }
}