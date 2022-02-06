package com.sh1p1lov.joydevmusicplayer.domain.validator

import com.sh1p1lov.joydevmusicplayer.domain.validator.models.ValidationResult

class LoginInputFieldValidator : InputFieldValidator() {

    companion object {
        const val MIN_TEXT_LENGTH = 6
    }

    override fun validate(text: String): ValidationResult {
        var errorMessage = ""
        var isValid = true

        if (text.isNotEmpty() && !Regex("""(\w|-)*""").matches(text)) {
            errorMessage = "Доступные символы: A-Z a-z 0-9 _ -"
            isValid = false
        }
        else if (text.isNotEmpty() && !Regex("""_?[a-zA-Z0-9]+(\w|-)*""").matches(text)) {
            errorMessage = "Может начинаться с буквы или цифры, или с одного символа '_' и затем буквы или цифры"
            isValid = false
        }
        else if (text.length < MIN_TEXT_LENGTH) {
            errorMessage = "Должен быть не менее $MIN_TEXT_LENGTH символов"
            isValid = false
        }

        return ValidationResult(isValid, errorMessage)
    }
}