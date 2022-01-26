package com.sh1p1lov.joydevmusicplayer.domain.validator

import com.sh1p1lov.joydevmusicplayer.domain.validator.models.ValidationResult

class LoginInputFieldValidator : InputFieldValidator() {

    companion object {
        const val MIN_TEXT_LENGTH = 6
        const val MAX_TEXT_LENGTH = 20
    }

    override fun validate(text: String): ValidationResult {
        TODO("Not yet implemented")
    }
}