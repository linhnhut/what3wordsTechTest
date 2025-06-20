package com.movie.feature.movies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.movie.core.design.component.MovieButton
import com.movie.core.design.icon.MovieIcons
import com.movie.core.design.component.MovieIconButton
import com.movie.core.ui.component.MovieCard
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MoviesScreen(
    onMovieClick: (Int) -> Unit,
    viewModel: MoviesViewModel = koinViewModel<MoviesViewModel>()
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    
    MoviesScreenContent(
        uiState = uiState,
        searchQuery = searchQuery,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        onClearSearch = viewModel::clearSearch,
        onRetry = viewModel::loadTrendingMovies,
        onMovieClick = onMovieClick
    )
}

@Composable
fun MoviesScreenContent(
    uiState: MoviesUiState,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onClearSearch: () -> Unit,
    onRetry: () -> Unit,
    onMovieClick: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Movies",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Search Input
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChanged,
                label = { Text("Search movies...") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                trailingIcon = if (searchQuery.isNotEmpty()) {
                    {
                        MovieIconButton(
                            onClick = onClearSearch,
                            icon = MovieIcons.Close,
                            contentDescription = "Clear search"
                        )
                    }
                } else null
            )
            
            // Content based on state
            when (val state = uiState) {
                is MoviesUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                
                is MoviesUiState.Success -> {
                    if (state.movies.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (state.isSearchResult) {
                                    "No movies found for \"$searchQuery\""
                                } else {
                                    "No trending movies available"
                                },
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    } else {
                        Column {
                            Text(
                                text = if (state.isSearchResult) "Search results" else "Trending movies",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            
                            LazyColumn(
                                contentPadding = PaddingValues(vertical = 8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(state.movies) { movie ->
                                    MovieCard(
                                        movie = movie,
                                        onClick = { onMovieClick(movie.id) }
                                    )
                                }
                            }
                        }
                    }
                }
                
                is MoviesUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = "Failed to load movies",
                                style = MaterialTheme.typography.titleMedium
                            )
                            
                            Text(
                                text = state.message,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            
                            MovieButton(
                                onClick = onRetry
                            ) {
                                Text("Retry")
                            }
                        }
                    }
                }
            }
        }
    }
}