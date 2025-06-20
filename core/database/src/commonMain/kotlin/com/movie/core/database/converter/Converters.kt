package com.movie.core.database.converter

import androidx.room.TypeConverter
import com.movie.core.model.Genre
import com.movie.core.model.ProductionCompany
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    private val json = Json { ignoreUnknownKeys = true }
    
    @TypeConverter
    fun fromGenresList(value: List<Genre>): String {
        return json.encodeToString(value)
    }
    
    @TypeConverter
    fun toGenresList(value: String): List<Genre> {
        return try {
            json.decodeFromString<List<Genre>>(value)
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    @TypeConverter
    fun fromProductionCompaniesList(value: List<ProductionCompany>): String {
        return json.encodeToString(value)
    }
    
    @TypeConverter
    fun toProductionCompaniesList(value: String): List<ProductionCompany> {
        return try {
            json.decodeFromString<List<ProductionCompany>>(value)
        } catch (e: Exception) {
            emptyList()
        }
    }
}