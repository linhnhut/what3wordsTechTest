package com.movie

import androidx.compose.ui.test.*
import com.movie.core.design.theme.MovieTheme
import com.movie.core.model.Movie
import com.movie.feature.movies.MoviesScreenContent
import com.movie.feature.movies.MoviesUiState
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalTestApi::class)
class MoviesScreenTest {
    
    @Test
    fun moviesScreen_displaysSearchField() = runComposeUiTest {
        // When
        setContent {
            MovieTheme {
                MoviesScreenContent(
                    uiState = MoviesUiState.Loading,
                    searchQuery = "",
                    onSearchQueryChanged = {},
                    onClearSearch = {},
                    onRetry = {},
                    onMovieClick = {}
                )
            }
        }
        
        // Then
        onNodeWithText("Search movies...").assertIsDisplayed()
    }
    
    @Test
    fun moviesScreen_displaysTrendingMoviesTitle() = runComposeUiTest {
        // Given
        val testMovies = listOf(
            createTestMovie(1, "Test Movie 1"),
            createTestMovie(2, "Test Movie 2")
        )
        
        // When
        setContent {
            MovieTheme {
                MoviesScreenContent(
                    uiState = MoviesUiState.Success(testMovies, isSearchResult = false),
                    searchQuery = "",
                    onSearchQueryChanged = {},
                    onClearSearch = {},
                    onRetry = {},
                    onMovieClick = {}
                )
            }
        }
        
        // Then
        onNodeWithText("Trending movies").assertIsDisplayed()
    }
    
    @Test
    fun moviesScreen_displaysTrendingMovies() = runComposeUiTest {
        // Given
        val testMovies = listOf(
            createTestMovie(1, "Test Movie 1"),
            createTestMovie(2, "Test Movie 2")
        )
        
        // When
        setContent {
            MovieTheme {
                MoviesScreenContent(
                    uiState = MoviesUiState.Success(testMovies, isSearchResult = false),
                    searchQuery = "",
                    onSearchQueryChanged = {},
                    onClearSearch = {},
                    onRetry = {},
                    onMovieClick = {}
                )
            }
        }
        
        // Then
        onNodeWithText("Test Movie 1").assertIsDisplayed()
        onNodeWithText("Test Movie 2").assertIsDisplayed()
    }
    
    @Test
    fun moviesScreen_displaysSearchResultsTitle() = runComposeUiTest {
        // Given
        val testMovies = listOf(
            createTestMovie(1, "Search Result Movie")
        )
        
        // When
        setContent {
            MovieTheme {
                MoviesScreenContent(
                    uiState = MoviesUiState.Success(testMovies, isSearchResult = true),
                    searchQuery = "search",
                    onSearchQueryChanged = {},
                    onClearSearch = {},
                    onRetry = {},
                    onMovieClick = {}
                )
            }
        }
        
        // Then
        onNodeWithText("Search results").assertIsDisplayed()
    }
    
    @Test
    fun moviesScreen_displaysClearButton_whenSearchHasText() = runComposeUiTest {
        // When
        setContent {
            MovieTheme {
                MoviesScreenContent(
                    uiState = MoviesUiState.Loading,
                    searchQuery = "test",
                    onSearchQueryChanged = {},
                    onClearSearch = {},
                    onRetry = {},
                    onMovieClick = {}
                )
            }
        }
        
        // Then
        onNode(hasContentDescription("Clear search")).assertIsDisplayed()
    }
    
    @Test
    fun moviesScreen_doesNotDisplayClearButton_whenSearchIsEmpty() = runComposeUiTest {
        // When
        setContent {
            MovieTheme {
                MoviesScreenContent(
                    uiState = MoviesUiState.Loading,
                    searchQuery = "",
                    onSearchQueryChanged = {},
                    onClearSearch = {},
                    onRetry = {},
                    onMovieClick = {}
                )
            }
        }
        
        // Then
        onNode(hasContentDescription("Clear search")).assertDoesNotExist()
    }
    
    @Test
    fun moviesScreen_displaysErrorState() = runComposeUiTest {
        // When
        setContent {
            MovieTheme {
                MoviesScreenContent(
                    uiState = MoviesUiState.Error("Network error"),
                    searchQuery = "",
                    onSearchQueryChanged = {},
                    onClearSearch = {},
                    onRetry = {},
                    onMovieClick = {}
                )
            }
        }
        
        // Then
        onNodeWithText("Failed to load movies").assertIsDisplayed()
        onNodeWithText("Network error").assertIsDisplayed()
        onNodeWithText("Retry").assertIsDisplayed()
    }
    
    @Test
    fun moviesScreen_displaysEmptyStateForSearch() = runComposeUiTest {
        // When
        setContent {
            MovieTheme {
                MoviesScreenContent(
                    uiState = MoviesUiState.Success(emptyList(), isSearchResult = true),
                    searchQuery = "test",
                    onSearchQueryChanged = {},
                    onClearSearch = {},
                    onRetry = {},
                    onMovieClick = {}
                )
            }
        }
        
        // Then
        onNodeWithText("No movies found for \"test\"").assertIsDisplayed()
    }
    
    @Test
    fun moviesScreen_displaysEmptyStateForTrending() = runComposeUiTest {
        // When
        setContent {
            MovieTheme {
                MoviesScreenContent(
                    uiState = MoviesUiState.Success(emptyList(), isSearchResult = false),
                    searchQuery = "",
                    onSearchQueryChanged = {},
                    onClearSearch = {},
                    onRetry = {},
                    onMovieClick = {}
                )
            }
        }
        
        // Then
        onNodeWithText("No trending movies available").assertIsDisplayed()
    }
    
    @Test
    fun moviesScreen_searchFieldUpdatesQuery() = runComposeUiTest {
        // Given
        var receivedQuery = ""
        
        // When
        setContent {
            MovieTheme {
                MoviesScreenContent(
                    uiState = MoviesUiState.Loading,
                    searchQuery = "",
                    onSearchQueryChanged = { receivedQuery = it },
                    onClearSearch = {},
                    onRetry = {},
                    onMovieClick = {}
                )
            }
        }
        
        // Perform search
        onNodeWithText("Search movies...").performTextInput("test query")
        
        // Then
        assertEquals("test query", receivedQuery)
    }
    
    @Test
    fun moviesScreen_clearButtonClearsSearch() = runComposeUiTest {
        // Given
        var clearCalled = false
        
        // When
        setContent {
            MovieTheme {
                MoviesScreenContent(
                    uiState = MoviesUiState.Loading,
                    searchQuery = "test",
                    onSearchQueryChanged = {},
                    onClearSearch = { clearCalled = true },
                    onRetry = {},
                    onMovieClick = {}
                )
            }
        }
        
        // Click clear button
        onNode(hasContentDescription("Clear search")).performClick()
        
        // Then
        assertEquals(true, clearCalled)
    }
    
    @Test
    fun moviesScreen_retryButtonTriggersRefresh() = runComposeUiTest {
        // Given
        var refreshCalled = false
        
        // When
        setContent {
            MovieTheme {
                MoviesScreenContent(
                    uiState = MoviesUiState.Error("Network error"),
                    searchQuery = "",
                    onSearchQueryChanged = {},
                    onClearSearch = {},
                    onRetry = { refreshCalled = true },
                    onMovieClick = {}
                )
            }
        }
        
        // Perform retry
        onNodeWithText("Retry").performClick()
        
        // Then
        assertEquals(true, refreshCalled)
    }
    
    @Test
    fun moviesScreen_movieCardClickTriggersCallback() = runComposeUiTest {
        // Given
        val testMovies = listOf(createTestMovie(1, "Test Movie"))
        var clickedMovieId = -1
        
        // When
        setContent {
            MovieTheme {
                MoviesScreenContent(
                    uiState = MoviesUiState.Success(testMovies, isSearchResult = false),
                    searchQuery = "",
                    onSearchQueryChanged = {},
                    onClearSearch = {},
                    onRetry = {},
                    onMovieClick = { clickedMovieId = it }
                )
            }
        }
        
        // Click on movie
        onNodeWithText("Test Movie").performClick()
        
        // Then
        assertEquals(1, clickedMovieId)
    }
    
    private fun createTestMovie(id: Int, title: String) = Movie(
        id = id,
        title = title,
        overview = "Test overview",
        posterPath = "/test.jpg",
        backdropPath = "/backdrop.jpg",
        releaseDate = "2023-01-01",
        voteAverage = 8.5,
        voteCount = 1000
    )
}