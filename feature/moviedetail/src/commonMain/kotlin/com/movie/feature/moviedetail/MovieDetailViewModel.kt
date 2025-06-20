package com.movie.feature.moviedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movie.core.data.repository.MovieRepository
import com.movie.core.model.MovieDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

/**
 * UI state for the Movie Detail screen
 */
sealed class MovieDetailUiState {
    object Loading : MovieDetailUiState()
    data class Success(val movieDetail: MovieDetail) : MovieDetailUiState()
    data class Error(val message: String) : MovieDetailUiState()
}

/**
 * ViewModel for the Movie Detail screen
 */
class MovieDetailViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<MovieDetailUiState>(MovieDetailUiState.Loading)
    val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()
    
    fun loadMovieDetail(movieId: Int, forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _uiState.value = MovieDetailUiState.Loading
            
            movieRepository.getMovieDetails(movieId, forceRefresh)
                .catch { exception ->
                    _uiState.value = MovieDetailUiState.Error(
                        exception.message ?: "Unknown error occurred"
                    )
                }
                .collect { movieDetail ->
                    _uiState.value = MovieDetailUiState.Success(movieDetail)
                }
        }
    }
}