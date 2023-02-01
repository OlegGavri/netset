package com.riggle.netset.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.riggle.netset.model.NetInfo
import com.riggle.netset.model.Nets

class NetworkListFragmentViewModel : ViewModel() {
    private val networkList : MutableLiveData<List<NetInfo>> by lazy {
        MutableLiveData<List<NetInfo>>()
    }

    init {
        val nets: Nets = Nets.getInstance()
        val netsObserver = Observer<List<NetInfo>> { nets ->
            networkList.value = nets
        }

        nets.addStateObserver(netsObserver)
    }

    fun getNetInfo(position: Int): NetInfo {
        TODO("Not yet implemented")
    }

    fun getNetCount(): Int {
        return networkList.value?.size ?: 0
    }
}