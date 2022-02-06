package com.sh1p1lov.joydevmusicplayer.domain.usecase

import com.sh1p1lov.joydevmusicplayer.domain.models.RegistrationUserParams
import com.sh1p1lov.joydevmusicplayer.domain.repository.UserRepository

class RegistrationByEmailUseCase(private val userRepository: UserRepository) {

    fun execute(registrationUserParams: RegistrationUserParams) {

    }
}