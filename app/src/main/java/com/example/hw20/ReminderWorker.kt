package com.example.hw20

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.hw20.data.ReminderDatabase
import com.example.hw20.model.Reminder
import com.example.hw20.fragments.add.AddFragment
import com.example.hw20.fragments.list.ListFragment
import com.example.hw20.repository.ReminderRepository
import com.example.hw20.viewmodel.ReminderViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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


