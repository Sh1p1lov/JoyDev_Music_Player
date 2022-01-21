package com.sh1p1lov.joydevmusicplayer.domain.validator

class LoginInputFieldValidator(minTextLength: Int, maxTextLength: Int)
    : InputFieldValidator(minTextLength, maxTextLength) {

    override fun validate(text: String) {
    }
}