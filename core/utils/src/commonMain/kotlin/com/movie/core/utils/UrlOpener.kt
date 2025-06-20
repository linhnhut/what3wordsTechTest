package com.movie.core.utils

/**
 * Platform-specific URL opener utility
 */
expect object UrlOpener {
    /**
     * Opens the given URL in the default browser or appropriate application
     * @param url The URL to open
     * @return true if the URL was successfully opened, false otherwise
     */
    fun openUrl(url: String): Boolean
}