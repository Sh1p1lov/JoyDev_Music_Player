package com.sh1p1lov.joydevmusicplayer.domain.usecase

import com.sh1p1lov.joydevmusicplayer.domain.validator.InputFieldValidator
import com.sh1p1lov.joydevmusicplayer.domain.validator.models.ValidationResult

class ValidateLoginInputFieldUseCase(private val inputFieldValidator: InputFieldValidator) {

    fun execute(text: String): ValidationResult {

        return inputFieldValidator.validate(text)
    }
}