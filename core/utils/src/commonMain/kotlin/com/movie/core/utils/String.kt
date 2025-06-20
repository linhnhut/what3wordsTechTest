package com.movie.core.utils

/**
 * Formats a number as a string with comma separators
 * @return formatted string with comma separators
 */
fun Long.formatNumberWithCommas(): String {
    val numberStr = toString()
    val reversed = numberStr.reversed()
    val withCommas = reversed.chunked(3).joinToString(",")
    return withCommas.reversed()
}

/**
 * Formats a number as a string with comma separators
 * @return formatted string with comma separators
 */
fun Int.formatNumberWithCommas(): String {
    return this.toLong().formatNumberWithCommas()
}