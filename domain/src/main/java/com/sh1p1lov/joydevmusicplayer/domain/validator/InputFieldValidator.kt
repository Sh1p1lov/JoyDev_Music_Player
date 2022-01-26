package com.sh1p1lov.joydevmusicplayer.domain.validator

import com.sh1p1lov.joydevmusicplayer.domain.validator.models.ValidationResult

abstract class InputFieldValidator {

    abstract fun validate(text: String): ValidationResult
}