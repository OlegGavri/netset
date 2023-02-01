package com.riggle.netset.model

import android.net.Network

data class NetInfo(
    val network : Network,

    val netName : String,
    val ipAddress : String,
    val netMask : String) {
}
