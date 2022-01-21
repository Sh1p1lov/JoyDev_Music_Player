package com.sh1p1lov.joydevmusicplayer.domain.validator

abstract class InputFieldValidator(minTextLength: Int, maxTextLength: Int) {

    init {
        if (minTextLength < 0 || maxTextLength < 0
            || maxTextLength < minTextLength) throw IllegalArgumentException()
    }

    abstract fun validate(text: String)
}