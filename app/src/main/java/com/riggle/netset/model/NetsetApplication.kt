package com.riggle.netset.model

import android.app.Application
import android.net.ConnectivityManager

class NetsetApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Nets.createInstance(this)
    }

    val connectivityManager : ConnectivityManager
        get() {
            return getSystemService(ConnectivityManager::class.java)
        }
}