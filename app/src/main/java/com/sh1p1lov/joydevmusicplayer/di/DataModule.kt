package com.sh1p1lov.joydevmusicplayer.di

import com.sh1p1lov.joydevmusicplayer.data.repository.UserRoomRepository
import com.sh1p1lov.joydevmusicplayer.data.room.database.AppDatabase
import com.sh1p1lov.joydevmusicplayer.domain.repository.UserRepository
import org.koin.dsl.module

val dataModule = module {

    single<AppDatabase> {
        AppDatabase.get(get())
    }

    single<UserRepository> {
        UserRoomRepository(database = get())
    }
}