package com.sh1p1lov.joydevmusicplayer.presentation.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sh1p1lov.joydevmusicplayer.R
import com.sh1p1lov.joydevmusicplayer.databinding.FragmentLoginBinding
import com.sh1p1lov.joydevmusicplayer.domain.models.LoginResult
import com.sh1p1lov.joydevmusicplayer.presentation.viewmodels.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment: Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val vm by viewModel<LoginViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLoginBinding.bind(view)

        binding.loginRegistrationHyperlink.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        binding.loginInputButton.setOnClickListener {
            vm.login(
                usernameOrEmail = binding.loginLoginInputField.text.toString(),
                password = binding.loginPasswordInputField.text.toString()
            )
        }
        vm.loginResult.observe(viewLifecycleOwner) {
            val toast = Toast.makeText(context, it.info, Toast.LENGTH_SHORT)
            toast.show()

            if (it.resultCode == LoginResult.OK) {
                findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
            }
        }
    }
}