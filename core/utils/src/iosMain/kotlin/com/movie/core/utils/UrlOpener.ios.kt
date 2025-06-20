package com.movie.core.utils

import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenURLOptionsKey
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * iOS implementation of UrlOpener
 */
actual object UrlOpener {
    @OptIn(ExperimentalForeignApi::class)
    actual fun openUrl(url: String): Boolean {
        return try {
            val nsUrl = NSURL.URLWithString(url) ?: return false
            val application = UIApplication.sharedApplication
            
            if (application.canOpenURL(nsUrl)) {
                application.openURL(nsUrl, options = emptyMap<Any?, Any?>(), null)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }
}