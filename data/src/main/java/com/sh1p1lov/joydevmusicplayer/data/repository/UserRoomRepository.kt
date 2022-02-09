package com.sh1p1lov.joydevmusicplayer.data.repository

import com.sh1p1lov.joydevmusicplayer.data.mappers.mapToUserEntity
import com.sh1p1lov.joydevmusicplayer.data.mappers.mapToUserInfo
import com.sh1p1lov.joydevmusicplayer.data.mappers.mapToUserInfoList
import com.sh1p1lov.joydevmusicplayer.data.room.database.AppDatabase
import com.sh1p1lov.joydevmusicplayer.domain.models.RegistrationUserParams
import com.sh1p1lov.joydevmusicplayer.domain.models.UserInfo
import com.sh1p1lov.joydevmusicplayer.domain.repository.UserRepository

class UserRoomRepository(private val database: AppDatabase) : UserRepository {
    override fun containsUserWithUsername(username: String): Boolean {
        return database.userDao().containsByUsername(username)
    }

    override fun containsUserWithEmail(email: String): Boolean {
        return database.userDao().containsByEmail(email)
    }

    override fun getUsers(): List<UserInfo> {
        val users = database.userDao().getAll()
        return mapToUserInfoList(userEntities = users)
    }

    override fun getUserWithUsername(username: String): UserInfo {
        val user = database.userDao().getByUsername(username)
        return mapToUserInfo(userEntity = user)
    }

    override fun getUserWithEmail(email: String): UserInfo {
        val user = database.userDao().getByEmail(email)
        return mapToUserInfo(userEntity = user)
    }

    override fun saveUser(userParams: RegistrationUserParams) {
        database.userDao().insert(mapToUserEntity(userParams))
    }

    override fun removeUser(username: String) {
        val user = database.userDao().getByUsername(username)
        database.userDao().delete(user)
    }
}