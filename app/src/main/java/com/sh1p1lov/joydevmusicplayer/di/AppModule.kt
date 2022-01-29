package com.sh1p1lov.joydevmusicplayer.di

import com.sh1p1lov.joydevmusicplayer.presentation.viewmodels.RegistrationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel<RegistrationViewModel> {
        RegistrationViewModel(
            registrationByEmailUseCase = get(),
            validateLoginInputFieldUseCase = get(),
            validatePasswordInputFieldUseCase = get()
        )
    }
}