package com.example.hw20.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.hw20.ReminderWorker
import com.example.hw20.data.ReminderDatabase
import com.example.hw20.repository.ReminderRepository
import com.example.hw20.model.Reminder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReminderViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Reminder>>

    val readReminders: LiveData<List<Reminder>>
    private val repository: ReminderRepository

    init {
        val reminderDao = ReminderDatabase.getDatabase(
            application
        ).reminderDao()
        repository = ReminderRepository(reminderDao)
        readReminders = repository.readReminders
        readAllData = repository.readAllData

    }

   fun displayAll() {
        viewModelScope.launch(Dispatchers.IO) {
           repository.displayAll()
        }
    }

    suspend fun addReminder(reminder: Reminder):Long{
       /* viewModelScope.launch(Dispatchers.IO) {
            repository.addReminder(reminder)
        }*/
        return withContext(Dispatchers.IO){
            repository.addReminder(reminder)
        }
    }

    suspend fun updateReminder(reminder: Reminder): Int{
        /*viewModelScope.launch(Dispatchers.IO){
            repository.updateReminder(reminder)
        }*/
        return withContext(Dispatchers.IO){
            repository.updateReminder(reminder)
        }

    }

    fun deleteReminder(reminder: Reminder){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteReminder(reminder)
        }
    }

    fun deleteAllReminders(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllReminders()
        }
    }

    fun updateReminderSeen(reminderId: Int, seen: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateReminderSeen(reminderId, seen)
        }
    }


}