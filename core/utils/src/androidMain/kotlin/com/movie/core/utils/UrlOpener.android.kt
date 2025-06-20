package com.movie.core.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import org.koin.java.KoinJavaComponent.inject

/**
 * Android implementation of UrlOpener
 */
actual object UrlOpener {
    private val context by inject<Context>(Context::class.java)
    
    actual fun openUrl(url: String): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
            true
        } catch (e: Exception) {
            false
        }
    }
}