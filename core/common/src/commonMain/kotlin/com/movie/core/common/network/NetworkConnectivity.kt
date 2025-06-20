package com.movie.core.common.network

interface INetworkConnectivity {
    suspend fun isConnected(): Boolean
}

expect object NetworkConnectivity: INetworkConnectivity {
    override suspend fun isConnected(): Boolean
}