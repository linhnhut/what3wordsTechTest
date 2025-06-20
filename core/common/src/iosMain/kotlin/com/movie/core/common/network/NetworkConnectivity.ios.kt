package com.movie.core.common.network

actual object NetworkConnectivity: INetworkConnectivity {
    actual override suspend fun isConnected(): Boolean {
        // Simplified implementation for iOS - always return true for now
        // In a real app, you'd use proper network reachability checking
        return true
    }
}