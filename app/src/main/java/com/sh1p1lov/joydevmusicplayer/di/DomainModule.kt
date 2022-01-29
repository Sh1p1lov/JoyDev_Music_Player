package com.sh1p1lov.joydevmusicplayer.di

import com.sh1p1lov.joydevmusicplayer.domain.usecase.RegistrationByEmailUseCase
import com.sh1p1lov.joydevmusicplayer.domain.usecase.ValidateLoginInputFieldUseCase
import com.sh1p1lov.joydevmusicplayer.domain.usecase.ValidatePasswordInputFieldUseCase
import com.sh1p1lov.joydevmusicplayer.domain.validator.LoginInputFieldValidator
import com.sh1p1lov.joydevmusicplayer.domain.validator.PasswordInputFieldValidator
import org.koin.dsl.module

val domainModule = module {

    factory {
        ValidateLoginInputFieldUseCase(LoginInputFieldValidator())
    }

    factory {
        ValidatePasswordInputFieldUseCase(PasswordInputFieldValidator())
    }

    factory {
        RegistrationByEmailUseCase(get())
    }
}