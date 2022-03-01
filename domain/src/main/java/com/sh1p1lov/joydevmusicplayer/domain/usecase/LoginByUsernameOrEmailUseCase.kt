package com.sh1p1lov.joydevmusicplayer.domain.usecase

import com.sh1p1lov.joydevmusicplayer.domain.models.*
import com.sh1p1lov.joydevmusicplayer.domain.repository.UserRepository

class LoginByUsernameOrEmailUseCase(private val userRepository: UserRepository) {

    fun execute(loginUserParams: LoginUserParams): LoginResult {
        var resultCode = LoginResult.OK
        var info = "Вход успешен"

        var user: UserInfo? =
            if (loginUserParams.useEmail) userRepository.getUserWithEmail(loginUserParams.usernameOrEmail)
            else userRepository.getUserWithUsername(loginUserParams.usernameOrEmail)

        if (user == null) {
            resultCode = LoginResult.NOT_FIND_USER
            info = "Неверный логин или пароль"
        }
        else if (user.passwordHashCode != loginUserParams.password.hashCode()) {
            resultCode = LoginResult.INVALID_PASSWORD
            info = "Неверный логин или пароль"
        }

        return LoginResult(resultCode, info)
    }
}