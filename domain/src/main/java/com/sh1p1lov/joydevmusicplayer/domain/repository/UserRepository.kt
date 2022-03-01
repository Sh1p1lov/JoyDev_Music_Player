package com.sh1p1lov.joydevmusicplayer.domain.repository

import com.sh1p1lov.joydevmusicplayer.domain.models.RegistrationUserParams
import com.sh1p1lov.joydevmusicplayer.domain.models.UserInfo

interface UserRepository {
    fun containsUserWithUsername(username: String): Boolean
    fun containsUserWithEmail(email: String): Boolean
    fun getUsers(): List<UserInfo>
    fun getUserWithUsername(username: String): UserInfo?
    fun getUserWithEmail(email: String): UserInfo?
    fun saveUser(userParams: RegistrationUserParams)
    fun removeUser(username: String)
}