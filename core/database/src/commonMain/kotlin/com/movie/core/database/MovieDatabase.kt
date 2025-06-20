package com.movie.core.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.movie.core.database.builder.defaultDatabase
import com.movie.core.database.converter.Converters
import com.movie.core.database.dao.MovieDao
import com.movie.core.database.entity.MovieDetailEntity
import com.movie.core.database.entity.MovieEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

internal expect object MovieDatabaseConstructor : RoomDatabaseConstructor<MovieDatabase> {
    override fun initialize(): MovieDatabase
}

@Database(
    entities = [MovieEntity::class, MovieDetailEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
@androidx.room.ConstructedBy(MovieDatabaseConstructor::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        const val DATABASE_NAME = "movie_database"

        fun get(): MovieDatabase {
            return defaultDatabase<MovieDatabase>( DATABASE_NAME)
        }
    }
}