package com.sh1p1lov.joydevmusicplayer.domain.usecase

import com.sh1p1lov.joydevmusicplayer.domain.models.RegistrationResult
import com.sh1p1lov.joydevmusicplayer.domain.models.RegistrationUserParams
import com.sh1p1lov.joydevmusicplayer.domain.repository.UserRepository

class RegistrationByEmailUseCase(private val userRepository: UserRepository) {

    fun execute(registrationUserParams: RegistrationUserParams): RegistrationResult {
        var resultCode = RegistrationResult.OK
        var info = "Регистрация успешна"

        if (userRepository.containsUserWithUsername(registrationUserParams.username)) {
            resultCode = RegistrationResult.USERNAME_EXISTS
            info = "Имя пользователя занято"
        }
        else if (userRepository.containsUserWithEmail(registrationUserParams.email)) {
            resultCode = RegistrationResult.EMAIL_EXISTS
            info = "E-mail занят"
        }
        else {
            userRepository.saveUser(registrationUserParams)
        }

        return RegistrationResult(resultCode, info)
    }
}