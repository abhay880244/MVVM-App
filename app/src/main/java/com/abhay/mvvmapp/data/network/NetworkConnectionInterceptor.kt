package com.abhay.mvvmapp.data.network

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.getSystemService
import com.abhay.mvvmapp.util.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        if (!isInternetAvailable())
            throw NoInternetException("Make sure to have active internet connection")
        return chain.proceed(chain.request())

    }

    private val applicationContext = context.applicationContext


    private fun isInternetAvailable(): Boolean {

        val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        connectivityManager.activeNetworkInfo.also {
            return it != null && it.isConnected
        }

    }


}