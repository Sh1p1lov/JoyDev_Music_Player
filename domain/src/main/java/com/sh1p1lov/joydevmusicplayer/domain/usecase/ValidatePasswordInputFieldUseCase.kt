package com.sh1p1lov.joydevmusicplayer.domain.usecase

import com.sh1p1lov.joydevmusicplayer.domain.validator.InputFieldValidator

class ValidatePasswordInputFieldUseCase(private val inputFieldValidator: InputFieldValidator) {

    fun execute(text: String) {

        inputFieldValidator.validate(text)
    }
}