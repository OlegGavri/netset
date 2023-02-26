package com.riggle.netset.model

import android.net.*
import android.util.Log
import androidx.lifecycle.Observer
import java.net.Inet4Address

// This class return information about networks
class Nets(application: NetsetApplication) {
    companion object {
        private var instance : Nets? = null

        fun getInstance() : Nets {
            return instance!!
        }

        fun createInstance(application : NetsetApplication) {
            instance = Nets(application)
        }

        const val TAG = "Net"
    }

    // It is lazy, because NetsetApplication.instance instantiated in onCreate function
    private val connectivityManager = application.connectivityManager

    val networkList : MutableList<NetInfo> = mutableListOf()
    private val observers : MutableList<Observer<List<NetInfo>>> = mutableListOf()

    init {

        // Register network monitoring
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                Log.d(TAG, "New network available: $network")
            }

            override fun onLosing(network: Network, maxMsToLive: Int) {
                Log.d(TAG, "Network lose: $network")
                removeNetwork(network)
            }

            override fun onLost(network: Network) {
                Log.d(TAG, "Network lost: $network")
                removeNetwork(network)
            }

            override fun onLinkPropertiesChanged(network: Network, linkProperties: LinkProperties) {
                //
                // Update parameters of the network in networkList
                //
                Log.d(TAG, "Network property changed $network: $linkProperties")

                //
                // Add new element in networkList
                //
                val interfaceName = linkProperties.interfaceName ?: "Unknown"
                val linkAddresses = linkProperties.linkAddresses

                var ipAddresses = String()
                for(linkAddress in linkAddresses) {
                    val address = linkAddress.address
                    val prefix = linkAddress.prefixLength
                    if(address is Inet4Address) {
                        val ip = address.hostAddress
                        ipAddresses += "$ip/$prefix\n"
                    }
                }

                // Remove ending newline
                ipAddresses = ipAddresses.dropLast(1)

                val netInfo = NetInfo(network, interfaceName, ipAddresses)

                if(netInfo !in networkList)
                    networkList.add(netInfo)

                for(observer in observers) {
                    observer.onChanged(networkList)
                }
            }
        }

        connectivityManager.registerNetworkCallback(request, networkCallback)
    }

    var current: NetInfo? = null

    fun addStateObserver(netsObserver: Observer<List<NetInfo>>) {
        observers.add(netsObserver)
    }

    //
    // Remove from networkList element for this network(with the same interface name)
    //
    private fun removeNetwork(network : Network) {
        val netInfo = networkList.find { it.network == network }
        networkList.remove(netInfo)

        for(observer in observers) {
            observer.onChanged(networkList)
        }
    }
}