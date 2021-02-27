package com.example.hw20

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.hw20.fragments.add.AddFragment
import com.example.hw20.fragments.list.ListFragment

class ReminderWorker(appContext:Context, workerParameters: WorkerParameters) :
        Worker(appContext,workerParameters) {

    override fun doWork(): Result {
        val text = inputData.getString("message") // this comes from the reminder parameters
        MainActivity.showNofitication(applicationContext,text!!)
        return   Result.success()
    }
}