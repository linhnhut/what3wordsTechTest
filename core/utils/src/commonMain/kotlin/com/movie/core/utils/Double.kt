package com.movie.core.utils

import kotlin.math.pow
import kotlin.math.round

/**
 * Formats a Double to a string with specified number of decimal places
 * @param decimalPlaces number of digits after the decimal point
 * @return formatted string representation of the double
 */
fun Double.toFormattedString(decimalPlaces: Int): String {
    val multiplier = 10.0.pow(decimalPlaces)
    val rounded = round(this * multiplier) / multiplier
    
    // Convert to string with specified decimal places
    val integerPart = rounded.toLong()
    val fractionalPart = round((rounded - integerPart) * multiplier).toLong()
    
    return if (decimalPlaces == 0) {
        integerPart.toString()
    } else {
        val fractionalString = fractionalPart.toString().padStart(decimalPlaces, '0')
        "$integerPart.$fractionalString"
    }
}