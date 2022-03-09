package com.sh1p1lov.joydevmusicplayer.data.room.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.sh1p1lov.joydevmusicplayer.data.room.entities.UserEntity

@Dao
interface UserDao {
    @Query("SELECT EXISTS(SELECT username FROM users WHERE username = :username)")
    fun containsByUsername(username: String): Boolean

    @Query("SELECT EXISTS(SELECT email FROM users WHERE username = :email)")
    fun containsByEmail(email: String): Boolean

    @Query("SELECT * FROM users")
    fun getAll(): List<UserEntity>

    @Query("SELECT * FROM users WHERE username = :username")
    fun getByUsername(username: String): UserEntity?

    @Query("SELECT * FROM users WHERE email = :email")
    fun getByEmail(email: String): UserEntity?

    @Insert
    fun insert(user: UserEntity)

    @Delete
    fun delete(user: UserEntity)
}