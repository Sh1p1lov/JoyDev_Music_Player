package com.sh1p1lov.joydevmusicplayer.domain.models

class LoginResult(val resultCode: Int, val info: String) {

    companion object {
        const val OK = 0
        const val NOT_FIND_USER = 1
        const val INVALID_PASSWORD = 2
    }
}