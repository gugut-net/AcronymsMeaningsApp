package com.example.acronymsmeaningsapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import javax.inject.Inject

object CommonUtils {

    private const val TAG = "Internet"

    private const val CELLULAR_MESSAGE = "NetworkCapabilities.TRANSPORT_CELLULAR"
    private const val WIFI_MESSAGE = "NetworkCapabilities.TRANSPORT_WIFI"
    private const val ETHERNET_MESSAGE = "NetworkCapabilities.TRANSPORT_ETHERNET"


    fun isOnline(connectivityManager: ConnectivityManager): Boolean {
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i(TAG, CELLULAR_MESSAGE)
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i(TAG, WIFI_MESSAGE)
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i(TAG, ETHERNET_MESSAGE)
                return true
            }
        }
        return false
    }


    fun getNetworkCapabilitiesMessage(transportType: Int): String {
        return when (transportType) {
            NetworkCapabilities.TRANSPORT_CELLULAR -> CELLULAR_MESSAGE
            NetworkCapabilities.TRANSPORT_WIFI -> WIFI_MESSAGE
            NetworkCapabilities.TRANSPORT_ETHERNET -> ETHERNET_MESSAGE
            else -> "Unknown transport type"
        }
    }


//    fun isOnline(connectivityManager: ConnectivityManager): Boolean {
//        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
//        if (capabilities != null) {
//            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
//                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
//                return true
//            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
//                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
//                return true
//            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
//                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
//                return true
//            }
//        }
//        return false
//    }
}