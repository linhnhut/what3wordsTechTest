package com.movie

import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.test.*
import com.movie.core.design.theme.MovieTheme
import com.movie.core.model.MovieDetail
import com.movie.core.model.Genre
import com.movie.feature.moviedetail.MovieDetailScreenContent
import com.movie.feature.moviedetail.MovieDetailUiState
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalTestApi::class)
class MovieDetailScreenTest {
    
    @Test
    fun movieDetailScreen_displaysLoadingState() = runComposeUiTest {
        // When
        setContent {
            MovieTheme {
                MovieDetailScreenContent(
                    uiState = MovieDetailUiState.Loading,
                    onBackClick = {},
                    onRetry = {}
                )
            }
        }
        
        // Then
        onNode(hasProgressBarRangeInfo(ProgressBarRangeInfo.Indeterminate)).assertIsDisplayed()
    }
    
    @Test
    fun movieDetailScreen_displaysMovieTitle() = runComposeUiTest {
        // Given
        val movieDetail = createTestMovieDetail()
        
        // When
        setContent {
            MovieTheme {
                MovieDetailScreenContent(
                    uiState = MovieDetailUiState.Success(movieDetail),
                    onBackClick = {},
                    onRetry = {}
                )
            }
        }
        
        // Then - Title appears in TopBar and Hero section
        onAllNodesWithText("Test Movie").assertCountEquals(2) // TopBar + Hero section
        onAllNodesWithText("Test Movie")[0].assertIsDisplayed() // Check first instance
    }
    
    @Test
    fun movieDetailScreen_displaysMovieOverview() = runComposeUiTest {
        // Given
        val movieDetail = createTestMovieDetail()
        
        // When
        setContent {
            MovieTheme {
                MovieDetailScreenContent(
                    uiState = MovieDetailUiState.Success(movieDetail),
                    onBackClick = {},
                    onRetry = {}
                )
            }
        }
        
        // Then
        onNodeWithText("This is a test movie overview").assertIsDisplayed()
    }
    
    @Test
    fun movieDetailScreen_displaysRatingAndVotes() = runComposeUiTest {
        // Given
        val movieDetail = createTestMovieDetail()
        
        // When
        setContent {
            MovieTheme {
                MovieDetailScreenContent(
                    uiState = MovieDetailUiState.Success(movieDetail),
                    onBackClick = {},
                    onRetry = {}
                )
            }
        }
        
        // Then
        onNodeWithText("⭐ 8.5").assertIsDisplayed()
        onNodeWithText("🗳️ 1000 votes").assertIsDisplayed()
    }
    
    @Test
    fun movieDetailScreen_displaysGenres() = runComposeUiTest {
        // Given
        val movieDetail = createTestMovieDetail()
        
        // When
        setContent {
            MovieTheme {
                MovieDetailScreenContent(
                    uiState = MovieDetailUiState.Success(movieDetail),
                    onBackClick = {},
                    onRetry = {}
                )
            }
        }
        
        // Then - Check that genres exist as comma-separated text
        onNodeWithText("Movie Information").assertExists()
        // Genres are displayed as "Action, Adventure" in DetailRow
        onNodeWithText("Action, Adventure").assertExists()
    }
    
    @Test
    fun movieDetailScreen_displaysRuntime() = runComposeUiTest {
        // Given
        val movieDetail = createTestMovieDetail()
        
        // When
        setContent {
            MovieTheme {
                MovieDetailScreenContent(
                    uiState = MovieDetailUiState.Success(movieDetail),
                    onBackClick = {},
                    onRetry = {}
                )
            }
        }
        
        // Then
        onNodeWithText("2h 0m").assertIsDisplayed()
    }
    
    @Test
    fun movieDetailScreen_displaysFinancialInfo_whenBudgetExists() = runComposeUiTest {
        // Given
        val movieDetail = createTestMovieDetail()
        
        // When
        setContent {
            MovieTheme {
                MovieDetailScreenContent(
                    uiState = MovieDetailUiState.Success(movieDetail),
                    onBackClick = {},
                    onRetry = {}
                )
            }
        }
        
        // Then
        onNodeWithText("Financial Information").assertIsDisplayed()
        onNodeWithText("Budget").assertIsDisplayed()
        onNodeWithText("$100,000,000").assertIsDisplayed()
    }
    
    @Test
    fun movieDetailScreen_displaysErrorState() = runComposeUiTest {
        // When
        setContent {
            MovieTheme {
                MovieDetailScreenContent(
                    uiState = MovieDetailUiState.Error("Network error"),
                    onBackClick = {},
                    onRetry = {}
                )
            }
        }
        
        // Then
        onNodeWithText("Failed to load movie details").assertIsDisplayed()
        onNodeWithText("Network error").assertIsDisplayed()
        onNodeWithText("Retry").assertIsDisplayed()
    }
    
    @Test
    fun movieDetailScreen_backButtonTriggersCallback() = runComposeUiTest {
        // Given
        val movieDetail = createTestMovieDetail()
        var backClicked = false
        
        // When
        setContent {
            MovieTheme {
                MovieDetailScreenContent(
                    uiState = MovieDetailUiState.Success(movieDetail),
                    onBackClick = { backClicked = true },
                    onRetry = {}
                )
            }
        }
        
        // Click back button
        onNode(hasContentDescription("Back")).performClick()
        
        // Then
        assertEquals(true, backClicked)
    }
    
    @Test
    fun movieDetailScreen_retryButtonTriggersRefresh() = runComposeUiTest {
        // Given
        var retryCalled = false
        
        // When
        setContent {
            MovieTheme {
                MovieDetailScreenContent(
                    uiState = MovieDetailUiState.Error("Network error"),
                    onBackClick = {},
                    onRetry = { retryCalled = true }
                )
            }
        }
        
        // Click retry
        onNodeWithText("Retry").performClick()
        
        // Then
        assertEquals(true, retryCalled)
    }
    
    @Test
    fun movieDetailScreen_displaysExternalLinks_whenAvailable() = runComposeUiTest {
        // Given
        val movieDetail = createTestMovieDetail()
        
        // When
        setContent {
            MovieTheme {
                MovieDetailScreenContent(
                    uiState = MovieDetailUiState.Success(movieDetail),
                    onBackClick = {},
                    onRetry = {}
                )
            }
        }
        
        // Then - Check that external links exist (they may be in scrollable content)
        onNodeWithText("External Links").assertExists()
        onNodeWithText("Visit Homepage").assertExists()
        onNodeWithText("View on IMDb").assertExists()
    }
    
    private fun createTestMovieDetail() = MovieDetail(
        id = 1,
        title = "Test Movie",
        overview = "This is a test movie overview",
        posterPath = "/test.jpg",
        backdropPath = "/backdrop.jpg",
        releaseDate = "2023-01-01",
        voteAverage = 8.5,
        voteCount = 1000,
        runtime = 120,
        genres = listOf(
            Genre(1, "Action"),
            Genre(2, "Adventure")
        ),
        homepage = "https://example.com",
        budget = 100000000,
        revenue = 200000000,
        status = "Released",
        tagline = "Test tagline",
        productionCompanies = emptyList(),
        productionCountries = emptyList(),
        spokenLanguages = emptyList(),
        imdbId = "tt1234567",
        originalTitle = "Test Movie Original",
        originCountry = listOf("US"),
        adult = false,
        popularity = 100.0,
        video = false,
        belongsToCollection = null
    )
}