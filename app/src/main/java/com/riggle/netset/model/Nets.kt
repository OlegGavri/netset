package com.riggle.netset.model

import android.net.*
import android.util.Log
import androidx.lifecycle.Observer

// This class return information about networks
class Nets {
    companion object {
        private var instance : Nets? = null

        fun getInstance() : Nets {
            if(instance == null) {
                instance = Nets()
            }
            return instance!!
        }

        const val TAG = "Net"
    }

    private val connectivityManager = NetsetApplication.instance!!.connectivityManager

    private val networkList : MutableList<NetInfo> = mutableListOf()
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
                Log.d(TAG, "Network lost: $network")
                //
                // Remove from networkList element for this network(with the same interface name)
                //
                val linkProperties = connectivityManager.getLinkProperties(network)
                val interfaceName = linkProperties?.interfaceName

                val netInfo = networkList.find { it.network == network }
                networkList.remove(netInfo)
            }

            override fun onLinkPropertiesChanged(network: Network, linkProperties: LinkProperties) {
                //
                // Update parameters of the network in networkList
                //
                Log.d(TAG, "Network property changed $network: $linkProperties")

                //
                // Add new element in networkList
                //
                val linkProperties = connectivityManager.getLinkProperties(network)
                val interfaceName = linkProperties?.interfaceName ?: "Unknown"

                val linkAddresses = linkProperties?.linkAddresses

                var ipAddresses = String()
                if (linkAddresses != null) {
                    for(linkAddress in linkAddresses) {
                        ipAddresses += linkAddress.address.hostAddress
                    }
                } else {
                    ipAddresses = "Unknown"
                }

                val netInfo = NetInfo(network, interfaceName, ipAddresses, ipAddresses)
                networkList.add(netInfo)

                for(observer in observers) {
                    observer.onChanged(networkList)
                }
            }
        }

        connectivityManager!!.registerNetworkCallback(request, networkCallback)
    }

    var current: NetInfo? = null

    fun addStateObserver(netsObserver: Observer<List<NetInfo>>) {
        observers.add(netsObserver)
    }
}