package com.movie.core.data.repository

import com.movie.core.common.network.INetworkConnectivity

object MockNetworkConnectivity: INetworkConnectivity {
    override suspend fun isConnected(): Boolean = true
}