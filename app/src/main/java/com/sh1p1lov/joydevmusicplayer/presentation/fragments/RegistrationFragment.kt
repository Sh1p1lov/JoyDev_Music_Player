package com.sh1p1lov.joydevmusicplayer.presentation.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sh1p1lov.joydevmusicplayer.R
import com.sh1p1lov.joydevmusicplayer.databinding.FragmentRegistrationBinding
import com.sh1p1lov.joydevmusicplayer.domain.models.RegistrationResult
import com.sh1p1lov.joydevmusicplayer.domain.models.RegistrationUserParams
import com.sh1p1lov.joydevmusicplayer.presentation.viewmodels.RegistrationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegistrationFragment : Fragment(R.layout.fragment_registration) {

    private lateinit var binding: FragmentRegistrationBinding
    private val vm by viewModel<RegistrationViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegistrationBinding.bind(view)

        binding.registrationLoginInputHyperlink.setOnClickListener {
            findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
        }

        binding.registrationLoginInputField.doAfterTextChanged {
            vm.validateLogin(it.toString())
        }
        vm.loginErrorMessage.observe(viewLifecycleOwner) {
            binding.registrationLoginInputLayout.error = it
        }

        binding.registrationEmailInputField.doAfterTextChanged {
            vm.validateEmail(it.toString())
        }
        vm.emailErrorMessage.observe(viewLifecycleOwner) {
            binding.registrationEmailInputLayout.error = it
        }

        binding.registrationPasswordInputField.doAfterTextChanged {
            vm.validatePassword(it.toString())
        }
        vm.passwordErrorMessage.observe(viewLifecycleOwner) {
            binding.registrationPasswordInputLayout.error = it
        }

        binding.registrationRepeatPasswordInputField.doAfterTextChanged {
            vm.validateRepeatedPassword(it.toString())
        }
        vm.repeatedPasswordErrorMessage.observe(viewLifecycleOwner) {
            binding.registrationRepeatPasswordInputLayout.error = it
        }

        binding.registrationButton.setOnClickListener {
            val registrationUserParams =
                RegistrationUserParams(
                    username = binding.registrationLoginInputField.text.toString(),
                    email = binding.registrationEmailInputField.text.toString(),
                    password = binding.registrationPasswordInputField.text.toString()
                )
            vm.registration(registrationUserParams)
        }
        vm.registrationResult.observe(viewLifecycleOwner) {
            val toast = Toast.makeText(context, it.info, Toast.LENGTH_SHORT)
            toast.show()

            if (it.resultCode == RegistrationResult.OK)
                findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
        }

        var isAgree = binding.registrationAgreeRulesCheckBox.isChecked
        binding.registrationButton.isClickable = isAgree
        binding.registrationAgreeRulesCheckBox.setOnClickListener {
            isAgree = binding.registrationAgreeRulesCheckBox.isChecked
            binding.registrationButton.isClickable = isAgree
        }
    }
}