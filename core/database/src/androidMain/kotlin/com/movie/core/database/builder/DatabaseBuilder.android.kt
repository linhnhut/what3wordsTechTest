package com.movie.core.database.builder

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.java.KoinJavaComponent.inject

actual inline fun <reified T : RoomDatabase> roomDatabaseBuilder(fileName: String): RoomDatabase.Builder<T> {
    val context by inject<Context>(Context::class.java)
    val dbFile = context.getDatabasePath(fileName)
    return Room.databaseBuilder<T>(
        context = context.applicationContext,
        name = dbFile.absolutePath
    )
}