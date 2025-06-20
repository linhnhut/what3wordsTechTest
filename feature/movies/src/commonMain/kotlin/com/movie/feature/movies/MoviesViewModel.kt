package com.movie.feature.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movie.core.data.repository.MovieRepository
import com.movie.core.model.Movie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

/**
 * Sealed class representing the different UI states for the Movies screen.
 * Follows the unidirectional data flow pattern for predictable state management.
 */
sealed class MoviesUiState {
    /**
     * Loading state while data is being fetched
     */
    object Loading : MoviesUiState()
    
    /**
     * Success state containing the list of movies
     * @property movies List of movies to display
     * @property isSearchResult Whether the movies are from search or trending
     */
    data class Success(val movies: List<Movie>, val isSearchResult: Boolean = false) : MoviesUiState()
    
    /**
     * Error state with error message
     * @property message User-friendly error message
     */
    data class Error(val message: String) : MoviesUiState()
}

/**
 * ViewModel for the Movies screen implementing MVVM pattern.
 * Manages the UI state for both trending movies and search functionality.
 * 
 * Features:
 * - Automatic search with 300ms debouncing to reduce API calls
 * - Combines trending movies display with search functionality
 * - Offline-first approach with caching for trending movies
 * - Error handling with user-friendly messages
 * 
 * @property movieRepository Repository for accessing movie data
 */
@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class MoviesViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    private val _uiState = MutableStateFlow<MoviesUiState>(MoviesUiState.Loading)
    val uiState: StateFlow<MoviesUiState> = _uiState.asStateFlow()
    
    init {
        loadTrendingMovies()
        setupSearchFlow()
    }
    
    /**
     * Sets up the reactive search flow with debouncing and automatic switching
     * between trending movies and search results based on query input.
     */
    private fun setupSearchFlow() {
        viewModelScope.launch {
            _searchQuery
                .debounce(300) // Wait 300ms after user stops typing to reduce API calls
                .distinctUntilChanged() // Only react to actual changes in query
                .flatMapLatest { query ->
                    _uiState.value = MoviesUiState.Loading
                    
                    when {
                        query.isBlank() || query.length < 2 -> {
                            // Show trending movies when search is empty
                            movieRepository.getTrendingMovies()
                                .catch { exception ->
                                    _uiState.value = MoviesUiState.Error(
                                        exception.message ?: "Failed to load trending movies"
                                    )
                                    flowOf(emptyList<Movie>())
                                }
                        }
                        else -> {
                            // Perform search
                            movieRepository.searchMovies(query)
                                .catch { exception ->
                                    _uiState.value = MoviesUiState.Error(
                                        exception.message ?: "Search failed"
                                    )
                                    flowOf(emptyList<Movie>())
                                }
                        }
                    }
                }
                .collect { movies ->
                    val isSearchResult = _searchQuery.value.isNotBlank() && _searchQuery.value.length >= 2
                    _uiState.value = MoviesUiState.Success(movies, isSearchResult)
                }
        }
    }
    
    /**
     * Updates the search query, triggering the debounced search flow.
     * Called when user types in the search field.
     * 
     * @param query New search query string
     */
    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }
    
    /**
     * Clears the search query and returns to trending movies.
     * Called when user taps clear button or wants to reset search.
     */
    fun clearSearch() {
        _searchQuery.value = ""
    }
    
    /**
     * Manually loads trending movies, typically called on initial load or refresh.
     * 
     * @param forceRefresh If true, bypasses cache and fetches fresh data
     */
    fun loadTrendingMovies(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _uiState.value = MoviesUiState.Loading
            
            movieRepository.getTrendingMovies(forceRefresh)
                .catch { exception ->
                    _uiState.value = MoviesUiState.Error(
                        exception.message ?: "Unknown error occurred"
                    )
                }
                .collect { movies ->
                    _uiState.value = MoviesUiState.Success(movies, isSearchResult = false)
                }
        }
    }
    
    /**
     * Refreshes the current data by forcing a network request.
     * Called when user performs pull-to-refresh gesture.
     */
    fun refresh() {
        loadTrendingMovies(forceRefresh = true)
    }
}