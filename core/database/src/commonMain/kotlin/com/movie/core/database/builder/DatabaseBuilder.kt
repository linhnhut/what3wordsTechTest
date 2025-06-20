package com.movie.core.database.builder

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO


expect inline fun <reified T : RoomDatabase> roomDatabaseBuilder(fileName: String): RoomDatabase.Builder<T>

inline fun <reified T : RoomDatabase> defaultDatabase(fileName: String): T {
    return roomDatabaseBuilder<T>(fileName)
        .fallbackToDestructiveMigrationOnDowngrade(true)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}