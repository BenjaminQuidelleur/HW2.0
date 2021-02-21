package com.example.hw20.repository

import androidx.lifecycle.LiveData
import com.example.hw20.data.ReminderDao
import com.example.hw20.model.Reminder

class ReminderRepository(private val reminderDao: ReminderDao) {
    val readAllData: LiveData<List<Reminder>> = reminderDao.readAllData()

    suspend fun addReminder(reminder: Reminder){
        reminderDao.addReminder(reminder)
    }

    suspend fun updateReminder(reminder: Reminder){
        reminderDao.updateReminder(reminder)
    }

    suspend fun deleteReminder(reminder: Reminder){
        reminderDao.deleteReminder(reminder)
    }

    suspend fun deleteAllReminders(){
        reminderDao.deleteAllReminders()
    }

}