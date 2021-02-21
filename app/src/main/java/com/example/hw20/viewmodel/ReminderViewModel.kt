package com.example.hw20.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.hw20.data.ReminderDatabase
import com.example.hw20.repository.ReminderRepository
import com.example.hw20.model.Reminder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReminderViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Reminder>>
    private val repository: ReminderRepository

    init {
        val reminderDao = ReminderDatabase.getDatabase(
            application
        ).reminderDao()
        repository = ReminderRepository(reminderDao)
        readAllData = repository.readAllData
    }

    fun addReminder(reminder: Reminder){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addReminder(reminder)
        }
    }

    fun updateReminder(reminder: Reminder){
        viewModelScope.launch(Dispatchers.IO){
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

}