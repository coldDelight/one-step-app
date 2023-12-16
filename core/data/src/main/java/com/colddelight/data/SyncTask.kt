package com.colddelight.data

import androidx.work.Constraints
import androidx.work.NetworkType

interface SyncTask {
    val syncConstraints: Constraints
        get() = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

    fun syncReq(id: Int)
}