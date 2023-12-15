package com.colddelight.data

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class SyncTaskImpl(
    private val context: Context
) : SyncTask {

    override fun syncReq(id: Int) {
        val inputData = Data.Builder()
            .putInt("id", id)
            .build()
        val uploadWorkRequest =
            OneTimeWorkRequestBuilder<CustomWorker>().addTag("SyncWorkManager")
                .setConstraints(syncConstraints).setInputData(inputData)
                .build()

        WorkManager.getInstance(context).enqueue(uploadWorkRequest)
    }
}