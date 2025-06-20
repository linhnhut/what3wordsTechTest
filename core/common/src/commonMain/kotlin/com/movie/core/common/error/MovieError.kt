package com.movie.core.common.error

/**
 * Sealed class hierarchy representing different types of errors that can occur
 * throughout the Movie application. Provides structured error handling and
 * user-friendly error messages.
 */
sealed class MovieError : Exception() {
    
    /**
     * Represents network-related errors (connectivity, timeout, DNS issues).
     */
    data class NetworkError(override val message: String = "Network connection failed") : MovieError()
    
    /**
     * Represents server-side errors (HTTP 4xx, 5xx responses).
     */
    data class ServerError(override val message: String = "Server error occurred") : MovieError()
    
    /**
     * Represents local cache/database operation errors.
     */
    data class CacheError(override val message: String = "Cache operation failed") : MovieError()
    
    /**
     * Represents cases where requested data could not be found.
     */
    data class DataNotFoundError(override val message: String = "Requested data not found") : MovieError()
    
    /**
     * Represents unexpected or unclassified errors.
     */
    data class UnknownError(override val message: String = "An unknown error occurred") : MovieError()
    
    companion object {
        /**
         * Converts a generic Throwable into a specific MovieError type
         * based on the error message content. This provides consistent
         * error classification throughout the application.
         * 
         * @param throwable The original exception to classify
         * @return Appropriate MovieError subtype
         */
        fun fromThrowable(throwable: Throwable): MovieError {
            return when {
                throwable.message?.contains("network", ignoreCase = true) == true ||
                throwable.message?.contains("connection", ignoreCase = true) == true ||
                throwable.message?.contains("timeout", ignoreCase = true) == true -> {
                    NetworkError(throwable.message ?: "Network connection failed")
                }
                throwable.message?.contains("server", ignoreCase = true) == true ||
                throwable.message?.contains("http", ignoreCase = true) == true -> {
                    ServerError(throwable.message ?: "Server error occurred")
                }
                throwable.message?.contains("cache", ignoreCase = true) == true ||
                throwable.message?.contains("database", ignoreCase = true) == true -> {
                    CacheError(throwable.message ?: "Cache operation failed")
                }
                throwable.message?.contains("not found", ignoreCase = true) == true -> {
                    DataNotFoundError(throwable.message ?: "Requested data not found")
                }
                else -> UnknownError(throwable.message ?: "An unknown error occurred")
            }
        }
    }
}