package com.sh1p1lov.joydevmusicplayer.domain.models

class RegistrationResult(val resultCode: Int, val info: String) {

    companion object {
        const val OK = 0
        const val USERNAME_EXISTS = 1
        const val EMAIL_EXISTS = 2
    }
}