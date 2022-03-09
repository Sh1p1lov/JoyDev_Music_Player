package com.sh1p1lov.joydevmusicplayer.data.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sh1p1lov.joydevmusicplayer.data.room.daos.UserDao
import com.sh1p1lov.joydevmusicplayer.data.room.entities.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun get(context: Context): AppDatabase {
            if (INSTANCE == null)
                INSTANCE = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "database")
                    .allowMainThreadQueries()
                    .build()
            return INSTANCE as AppDatabase
        }
    }

    abstract fun userDao(): UserDao
}