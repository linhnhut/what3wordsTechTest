package com.movie.core.common.network

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import org.koin.java.KoinJavaComponent.inject

actual object NetworkConnectivity: INetworkConnectivity {
    private val context by inject<Context>(Context::class.java)
    
    @androidx.annotation.RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    actual override suspend fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }
}