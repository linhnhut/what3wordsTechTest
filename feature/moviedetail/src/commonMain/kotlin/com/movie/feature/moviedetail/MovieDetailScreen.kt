package com.movie.feature.moviedetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.movie.core.design.component.MovieButton
import com.movie.core.design.component.MovieCard
import com.movie.core.design.component.MovieIconButton
import com.movie.core.design.icon.MovieIcons
import com.movie.core.utils.UrlOpener
import com.movie.core.utils.formatNumberWithCommas
import com.movie.core.utils.toFormattedString
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MovieDetailScreen(
    movieId: Int,
    onBackClick: () -> Unit,
    viewModel: MovieDetailViewModel = koinViewModel<MovieDetailViewModel>()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(movieId) {
        viewModel.loadMovieDetail(movieId)
    }
    
    MovieDetailScreenContent(
        uiState = uiState,
        onBackClick = onBackClick,
        onRetry = { viewModel.loadMovieDetail(movieId) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreenContent(
    uiState: MovieDetailUiState,
    onBackClick: () -> Unit,
    onRetry: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (val state = uiState) {
                            is MovieDetailUiState.Success -> state.movieDetail.title
                            else -> "Movie Details"
                        },
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    MovieIconButton(
                        onClick = onBackClick,
                        icon = MovieIcons.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            )
        }
    ) { paddingValues ->
        when (val state = uiState) {
            is MovieDetailUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            
            is MovieDetailUiState.Success -> {
                val movieDetail = state.movieDetail
                
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    // Background backdrop image
                    movieDetail.fullBackdropUrl?.let { backdropUrl ->
                        AsyncImage(
                            model = backdropUrl,
                            contentDescription = movieDetail.title,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        
                        // Gradient overlay
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
                                            MaterialTheme.colorScheme.background
                                        ),
                                        startY = 0f,
                                        endY = 800f
                                    )
                                )
                        )
                    }
                    
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Hero Section with Poster
                        MovieCard {
                            Column {
                                // Movie Info with Poster
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    // Poster Image
                                    movieDetail.fullPosterUrl?.let { posterUrl ->
                                        AsyncImage(
                                            model = posterUrl,
                                            contentDescription = movieDetail.title,
                                            modifier = Modifier
                                                .width(120.dp)
                                                .aspectRatio(2f / 3f)
                                                .clip(MaterialTheme.shapes.medium),
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                    
                                    // Movie Details
                                    Column(
                                        modifier = Modifier.weight(1f),
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Text(
                                            text = movieDetail.title,
                                            style = MaterialTheme.typography.headlineMedium,
                                            fontWeight = FontWeight.Bold
                                        )
                                        
                                        Row(
                                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Row(
                                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Icon(
                                                    imageVector = MovieIcons.Star,
                                                    contentDescription = "Rating",
                                                    modifier = Modifier.size(16.dp),
                                                    tint = MaterialTheme.colorScheme.primary
                                                )
                                                Text(
                                                    text = movieDetail.voteAverage.toFormattedString(1),
                                                    style = MaterialTheme.typography.bodyLarge,
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                            }
                                            
                                            Row(
                                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Icon(
                                                    imageVector = MovieIcons.Vote,
                                                    contentDescription = "Votes",
                                                    modifier = Modifier.size(16.dp),
                                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                                )
                                                Text(
                                                    text = "${movieDetail.voteCount} votes",
                                                    style = MaterialTheme.typography.bodyMedium,
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                                )
                                            }
                                        }
                                        
                                        if (movieDetail.releaseDate.isNotEmpty()) {
                                            Row(
                                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Icon(
                                                    imageVector = MovieIcons.Calendar,
                                                    contentDescription = "Release date",
                                                    modifier = Modifier.size(16.dp),
                                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                                )
                                                Text(
                                                    text = "${movieDetail.releaseDate} (${movieDetail.year})",
                                                    style = MaterialTheme.typography.bodyMedium,
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        
                        // Overview Card
                        if (movieDetail.overview.isNotEmpty()) {
                            MovieCard {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "Overview",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold
                                    )
                                    
                                    Text(
                                        text = movieDetail.overview,
                                        style = MaterialTheme.typography.bodyMedium,
                                        lineHeight = MaterialTheme.typography.bodyMedium.lineHeight
                                    )
                                }
                            }
                        }
                        
                        // Tagline Card
                        if (!movieDetail.tagline.isNullOrEmpty()) {
                            MovieCard {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = movieDetail.tagline!!,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Medium,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                        
                        // Movie Info Card
                        MovieCard {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "Movie Information",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )
                                
                                if (movieDetail.genres.isNotEmpty()) {
                                    DetailRow(
                                        label = "Genres",
                                        value = movieDetail.genres.joinToString(", ") { it.name }
                                    )
                                }
                                
                                if (movieDetail.runtime != null && movieDetail.runtime!! > 0) {
                                    DetailRow(
                                        label = "Runtime",
                                        value = movieDetail.formattedRuntime ?: "${movieDetail.runtime} minutes"
                                    )
                                }
                                
                                if (movieDetail.status.isNotEmpty()) {
                                    DetailRow(
                                        label = "Status",
                                        value = movieDetail.status
                                    )
                                }
                                
                                if (!movieDetail.originalTitle.isNullOrEmpty() && movieDetail.originalTitle != movieDetail.title) {
                                    DetailRow(
                                        label = "Original Title",
                                        value = movieDetail.originalTitle!!
                                    )
                                }
                                
                                if (movieDetail.spokenLanguages.isNotEmpty()) {
                                    DetailRow(
                                        label = "Languages",
                                        value = movieDetail.spokenLanguages.joinToString(", ") { it.englishName }
                                    )
                                }
                                
                                if (movieDetail.productionCountries.isNotEmpty()) {
                                    DetailRow(
                                        label = "Countries",
                                        value = movieDetail.productionCountries.joinToString(", ") { it.name }
                                    )
                                }
                                
                                if (movieDetail.popularity > 0) {
                                    DetailRow(
                                        label = "Popularity",
                                        value = movieDetail.popularity.toFormattedString(1)
                                    )
                                }
                            }
                        }
                        
                        // Financial Info Card
                        if (movieDetail.budget > 0 || movieDetail.revenue > 0) {
                            MovieCard {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "Financial Information",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold
                                    )
                                    
                                    if (movieDetail.budget > 0) {
                                        DetailRow(
                                            label = "Budget",
                                            value = "$${movieDetail.budget.formatNumberWithCommas()}"
                                        )
                                    }
                                    
                                    if (movieDetail.revenue > 0) {
                                        DetailRow(
                                            label = "Revenue",
                                            value = "$${movieDetail.revenue.formatNumberWithCommas()}"
                                        )
                                    }
                                }
                            }
                        }
                        
                        // Production Card
                        if (movieDetail.productionCompanies.isNotEmpty()) {
                            MovieCard {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "Production",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold
                                    )

                                    Text(
                                        text = "Production Companies",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )

                                    Text(
                                        text = movieDetail.productionCompanies.joinToString(", ") { it.name },
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                        
                        // Collection Card
                        movieDetail.belongsToCollection?.let { collection ->
                            MovieCard {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "Part of Collection",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold
                                    )
                                    
                                    Text(
                                        text = collection.name,
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                        
                        // External Links Card
                        if (!movieDetail.homepage.isNullOrEmpty() || !movieDetail.imdbId.isNullOrEmpty()) {
                            MovieCard {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Text(
                                        text = "External Links",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold
                                    )
                                    
                                    if (!movieDetail.homepage.isNullOrEmpty()) {
                                        MovieButton(
                                            onClick = {
                                                UrlOpener.openUrl(movieDetail.homepage!!)
                                            },
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text("Visit Homepage")
                                        }
                                    }
                                    
                                    if (!movieDetail.imdbId.isNullOrEmpty()) {
                                        MovieButton(
                                            onClick = { 
                                                UrlOpener.openUrl("https://www.imdb.com/title/${movieDetail.imdbId}")
                                            },
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text("View on IMDb")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            is MovieDetailUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Failed to load movie details",
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

@Composable
fun DetailRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}