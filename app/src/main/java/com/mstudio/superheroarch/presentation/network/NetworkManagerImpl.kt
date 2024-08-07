package com.mstudio.superheroarch.presentation.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.mstudio.superheroarch.RickAndMortyApplication

class NetworkManagerImpl(private val context: Context = RickAndMortyApplication.instance) : NetworkManager {
    override fun hasInternetConnection(): Boolean {
        return (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
            getNetworkCapabilities(activeNetwork)?.run {
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                        || hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                        || hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            } ?: false
        }
    }
}