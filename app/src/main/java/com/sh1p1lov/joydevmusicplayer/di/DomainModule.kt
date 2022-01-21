package com.sh1p1lov.joydevmusicplayer.di

import com.sh1p1lov.joydevmusicplayer.domain.usecase.RegistrationByEmailUseCase
import com.sh1p1lov.joydevmusicplayer.domain.usecase.ValidateEmailInputFieldUseCase
import com.sh1p1lov.joydevmusicplayer.domain.usecase.ValidateLoginInputFieldUseCase
import com.sh1p1lov.joydevmusicplayer.domain.usecase.ValidatePasswordInputFieldUseCase
import com.sh1p1lov.joydevmusicplayer.domain.validator.EmailInputFieldValidator
import com.sh1p1lov.joydevmusicplayer.domain.validator.LoginInputFieldValidator
import com.sh1p1lov.joydevmusicplayer.domain.validator.PasswordInputFieldValidator
import org.koin.dsl.module

private const val MIN_LOGIN_TEXT_LENGTH = 6
private const val MAX_LOGIN_TEXT_LENGTH = 20
private const val MIN_EMAIL_TEXT_LENGTH = 6
private const val MAX_EMAIL_TEXT_LENGTH = 256
private const val MIN_PASSWORD_TEXT_LENGTH = 6
private const val MAX_PASSWORD_TEXT_LENGTH = 20

val domainModule = module {

    factory {
        ValidateLoginInputFieldUseCase(
            LoginInputFieldValidator(
            minTextLength = MIN_LOGIN_TEXT_LENGTH,
            maxTextLength = MAX_LOGIN_TEXT_LENGTH
        ))
    }

    factory {
        ValidateEmailInputFieldUseCase(
            EmailInputFieldValidator(
                minTextLength = MIN_EMAIL_TEXT_LENGTH,
                maxTextLength = MAX_EMAIL_TEXT_LENGTH
            ))
    }

    factory {
        ValidatePasswordInputFieldUseCase(
            PasswordInputFieldValidator(
                minTextLength = MIN_PASSWORD_TEXT_LENGTH,
                maxTextLength = MAX_PASSWORD_TEXT_LENGTH
            ))
    }

    factory {
        RegistrationByEmailUseCase(get())
    }
}