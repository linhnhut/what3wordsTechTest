package com.movie.core.ui.util

/**
 * Utility functions for handling TMDB image URLs
 */
object ImageUtil {
    private const val TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"
    
    // Image sizes for different use cases
    private const val POSTER_SIZE_SMALL = "w185"
    private const val POSTER_SIZE_MEDIUM = "w342"
    private const val POSTER_SIZE_LARGE = "w500"
    private const val BACKDROP_SIZE_SMALL = "w300"
    private const val BACKDROP_SIZE_MEDIUM = "w780"
    private const val BACKDROP_SIZE_LARGE = "w1280"
    
    /**
     * Constructs a full URL for a poster image
     * @param posterPath The poster path from TMDB API (e.g., "/abc123.jpg")
     * @param size The desired image size (small, medium, large)
     * @return Full TMDB image URL or null if posterPath is null/empty
     */
    fun getPosterUrl(
        posterPath: String?,
        size: ImageSize = ImageSize.MEDIUM
    ): String? {
        if (posterPath.isNullOrEmpty()) return null
        
        val sizeString = when (size) {
            ImageSize.SMALL -> POSTER_SIZE_SMALL
            ImageSize.MEDIUM -> POSTER_SIZE_MEDIUM
            ImageSize.LARGE -> POSTER_SIZE_LARGE
        }
        
        return "$TMDB_IMAGE_BASE_URL$sizeString$posterPath"
    }
    
    /**
     * Constructs a full URL for a backdrop image
     * @param backdropPath The backdrop path from TMDB API (e.g., "/xyz789.jpg")
     * @param size The desired image size (small, medium, large)
     * @return Full TMDB image URL or null if backdropPath is null/empty
     */
    fun getBackdropUrl(
        backdropPath: String?,
        size: ImageSize = ImageSize.MEDIUM
    ): String? {
        if (backdropPath.isNullOrEmpty()) return null
        
        val sizeString = when (size) {
            ImageSize.SMALL -> BACKDROP_SIZE_SMALL
            ImageSize.MEDIUM -> BACKDROP_SIZE_MEDIUM
            ImageSize.LARGE -> BACKDROP_SIZE_LARGE
        }
        
        return "$TMDB_IMAGE_BASE_URL$sizeString$backdropPath"
    }
}

enum class ImageSize {
    SMALL,
    MEDIUM,
    LARGE
}