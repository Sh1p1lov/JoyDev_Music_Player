package com.sh1p1lov.joydevmusicplayer.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sh1p1lov.joydevmusicplayer.domain.models.RegistrationUserParams
import com.sh1p1lov.joydevmusicplayer.domain.usecase.RegistrationByEmailUseCase
import com.sh1p1lov.joydevmusicplayer.domain.usecase.ValidateLoginInputFieldUseCase
import com.sh1p1lov.joydevmusicplayer.domain.usecase.ValidatePasswordInputFieldUseCase

class RegistrationViewModel(
    private val registrationByEmailUseCase: RegistrationByEmailUseCase,
    private val validateLoginInputFieldUseCase: ValidateLoginInputFieldUseCase,
    private val validatePasswordInputFieldUseCase: ValidatePasswordInputFieldUseCase
) : ViewModel() {

    private var mutableLoginErrorMessage = MutableLiveData<String>()
    val loginErrorMessage: LiveData<String> = mutableLoginErrorMessage

    private var mutableEmailErrorMessage = MutableLiveData<String>()
    val emailErrorMessage: LiveData<String> = mutableEmailErrorMessage

    private var passwordText = ""
    private var mutablePasswordErrorMessage = MutableLiveData<String>()
    val passwordErrorMessage: LiveData<String> = mutablePasswordErrorMessage

    private var mutableRepeatedPasswordErrorMessage = MutableLiveData<String>()
    val repeatedPasswordErrorMessage: LiveData<String> = mutableRepeatedPasswordErrorMessage

    fun registration(userParams: RegistrationUserParams) {
        registrationByEmailUseCase.execute(userParams)
    }

    fun validateLogin(text: String) {
        val validationResult = validateLoginInputFieldUseCase.execute(text)
        mutableLoginErrorMessage.value = validationResult.errorMessage
    }

    fun validateEmail(text: String) {
        val isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()
        mutableEmailErrorMessage.value = if (isValid) "" else "Некорректный формат электронной почты"
    }

    fun validatePassword(text: String) {
        passwordText = text
        val validationResult = validatePasswordInputFieldUseCase.execute(text)
        mutablePasswordErrorMessage.value = validationResult.errorMessage
    }

    fun validateRepeatedPassword(text: String) {
        val isValid = passwordText == text
        mutableRepeatedPasswordErrorMessage.value = if (isValid) "" else "Пароли не совпадают"
    }
}