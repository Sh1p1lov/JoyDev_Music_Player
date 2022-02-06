package com.sh1p1lov.joydevmusicplayer.di

import com.sh1p1lov.joydevmusicplayer.domain.repository.UserRepository
import org.koin.dsl.module

val dataModule = module {

    single<UserRepository> {
        object : UserRepository {}
    }
}