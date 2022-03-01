package com.sh1p1lov.joydevmusicplayer.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sh1p1lov.joydevmusicplayer.domain.models.RegistrationResult
import com.sh1p1lov.joydevmusicplayer.domain.models.RegistrationUserParams
import com.sh1p1lov.joydevmusicplayer.domain.usecase.RegistrationByEmailUseCase
import com.sh1p1lov.joydevmusicplayer.domain.usecase.ValidateLoginInputFieldUseCase
import com.sh1p1lov.joydevmusicplayer.domain.usecase.ValidatePasswordInputFieldUseCase

class RegistrationViewModel(
    private val registrationByEmailUseCase: RegistrationByEmailUseCase,
    private val validateLoginInputFieldUseCase: ValidateLoginInputFieldUseCase,
    private val validatePasswordInputFieldUseCase: ValidatePasswordInputFieldUseCase
) : ViewModel() {

    private var isValidLogin = false
    private var mutableLoginErrorMessage = MutableLiveData<String>()
    val loginErrorMessage: LiveData<String> = mutableLoginErrorMessage

    private var isValidEmail = false
    private var mutableEmailErrorMessage = MutableLiveData<String>()
    val emailErrorMessage: LiveData<String> = mutableEmailErrorMessage

    private var isValidPassword = false
    private var passwordText = ""
    private var mutablePasswordErrorMessage = MutableLiveData<String>()
    val passwordErrorMessage: LiveData<String> = mutablePasswordErrorMessage

    private var isPasswordRepeated = false
    private var mutableRepeatedPasswordErrorMessage = MutableLiveData<String>()
    val repeatedPasswordErrorMessage: LiveData<String> = mutableRepeatedPasswordErrorMessage

    private var mutableRegistrationResult = MutableLiveData<RegistrationResult>()
    val registrationResult: LiveData<RegistrationResult> = mutableRegistrationResult

    fun registration(userParams: RegistrationUserParams) {
        if (!isValidLogin && !isValidEmail && !isValidPassword && !isPasswordRepeated) return
        mutableRegistrationResult.value = registrationByEmailUseCase.execute(userParams)
    }

    fun validateLogin(text: String) {
        val validationResult = validateLoginInputFieldUseCase.execute(text)
        mutableLoginErrorMessage.value = validationResult.errorMessage
        isValidLogin = validationResult.isValid
    }

    fun validateEmail(text: String) {
        isValidEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()
        mutableEmailErrorMessage.value = if (isValidEmail) "" else "Некорректный формат электронной почты"
    }

    fun validatePassword(text: String) {
        passwordText = text
        val validationResult = validatePasswordInputFieldUseCase.execute(text)
        mutablePasswordErrorMessage.value = validationResult.errorMessage
        isValidPassword = validationResult.isValid
    }

    fun validateRepeatedPassword(text: String) {
        isPasswordRepeated = passwordText == text
        mutableRepeatedPasswordErrorMessage.value = if (isPasswordRepeated) "" else "Пароли не совпадают"
    }
}