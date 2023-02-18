package com.riggle.netset.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.riggle.netset.model.NetInfo
import com.riggle.netset.model.Nets

class NetworkListFragmentViewModel : ViewModel() {
    val networkList : MutableLiveData<List<NetInfo>> by lazy {
        MutableLiveData<List<NetInfo>>()
    }

    init {
        val nets: Nets = Nets.getInstance()
        val netsObserver = Observer<List<NetInfo>> { newNetworkList ->
            networkList.value = newNetworkList
        }

        nets.addStateObserver(netsObserver)
        networkList.value = nets.networkList
    }

    fun getNetInfo(position: Int): NetInfo {
        return networkList.value!![position]
    }

    fun getNetCount(): Int {
        return networkList.value?.size ?: 0
    }
}