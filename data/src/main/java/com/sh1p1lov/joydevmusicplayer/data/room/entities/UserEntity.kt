package com.sh1p1lov.joydevmusicplayer.data.room.entities

import androidx.room.Entity

@Entity(tableName = "users", primaryKeys = ["username", "email"])
data class UserEntity(
    val username: String,
    val email: String,
    val password: String
)
