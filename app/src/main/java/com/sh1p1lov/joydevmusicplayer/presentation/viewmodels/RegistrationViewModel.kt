package com.sh1p1lov.joydevmusicplayer.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.sh1p1lov.joydevmusicplayer.domain.models.RegistrationUserParams
import com.sh1p1lov.joydevmusicplayer.domain.usecase.RegistrationByEmailUseCase
import com.sh1p1lov.joydevmusicplayer.domain.usecase.ValidateEmailInputFieldUseCase
import com.sh1p1lov.joydevmusicplayer.domain.usecase.ValidateLoginInputFieldUseCase
import com.sh1p1lov.joydevmusicplayer.domain.usecase.ValidatePasswordInputFieldUseCase

class RegistrationViewModel(
    private val registrationByEmailUseCase: RegistrationByEmailUseCase,
    private val validateLoginInputFieldUseCase: ValidateLoginInputFieldUseCase,
    private val validateEmailInputFieldUseCase: ValidateEmailInputFieldUseCase,
    private val validatePasswordInputFieldUseCase: ValidatePasswordInputFieldUseCase
) : ViewModel() {

    fun registration(userParams: RegistrationUserParams) {
        registrationByEmailUseCase.execute(userParams)
    }

    fun validateLogin(text: String) {
        validateLoginInputFieldUseCase.execute(text)
    }

    fun validateEmail(text: String) {
        validateEmailInputFieldUseCase.execute(text)
    }

    fun validatePassword(text: String) {
        validatePasswordInputFieldUseCase.execute(text)
    }
}