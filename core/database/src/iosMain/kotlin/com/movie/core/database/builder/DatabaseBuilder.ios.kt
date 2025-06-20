package com.movie.core.database.builder

import androidx.room.Room
import androidx.room.RoomDatabase
import com.movie.core.database.MovieDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSHomeDirectory
import platform.Foundation.NSUserDomainMask

actual inline fun <reified T : RoomDatabase> roomDatabaseBuilder(fileName: String): RoomDatabase.Builder<T> {
    val dbFilePath = documentDirectory() + "/${fileName}"
    return Room.databaseBuilder<T>(
        name = dbFilePath,
    )
}

@OptIn(ExperimentalForeignApi::class)
fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}
