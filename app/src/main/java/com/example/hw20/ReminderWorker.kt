package com.example.hw20

import android.app.Application
import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.hw20.data.ReminderDatabase
import com.example.hw20.repository.ReminderRepository

class ReminderWorker(appContext:Context, workerParameters: WorkerParameters) :
        Worker(appContext,workerParameters) {




    override fun doWork(): Result {



        val text = inputData.getString("message").toString()// this comes from the reminder parameters
        val reminderId = inputData.getInt("reminderId",0)

        updateReminderSeen(reminderId)



        MainActivity.showNofitication(applicationContext,text!!)

        return   Result.success()
    }

    private fun updateReminderSeen(reminderId: Int){
        val application = Application()
        val reminderDao = ReminderDatabase.getDatabase(application).reminderDao()
        val repository = ReminderRepository(reminderDao)
        repository.updateReminderSeen(reminderId, true)
    }
}


