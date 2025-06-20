package com.movie.core.database.dao

import androidx.room.*
import com.movie.core.database.entity.MovieEntity
import com.movie.core.database.entity.MovieDetailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies WHERE isTrending = 1 ORDER BY voteAverage DESC")
    fun getTrendingMovies(): Flow<List<MovieEntity>>
    
    @Query("SELECT * FROM movies WHERE isTrending = 1 ORDER BY voteAverage DESC")
    suspend fun getTrendingMoviesSync(): List<MovieEntity>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)
    
    @Query("DELETE FROM movies WHERE isTrending = 1")
    suspend fun deleteTrendingMovies()
    
    @Transaction
    suspend fun replaceTrendingMovies(movies: List<MovieEntity>) {
        deleteTrendingMovies()
        insertMovies(movies.map { it.copy(isTrending = true) })
    }
    
    @Query("SELECT * FROM movie_details WHERE id = :movieId")
    suspend fun getMovieDetail(movieId: Int): MovieDetailEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetail(movieDetail: MovieDetailEntity)
    
    @Query("SELECT cachedAt FROM movies WHERE isTrending = 1 LIMIT 1")
    suspend fun getTrendingCacheTime(): Long?
    
    @Query("SELECT cachedAt FROM movie_details WHERE id = :movieId")
    suspend fun getMovieDetailCacheTime(movieId: Int): Long?
}