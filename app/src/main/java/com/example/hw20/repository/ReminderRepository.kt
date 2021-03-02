package com.example.hw20.repository

import androidx.lifecycle.LiveData
import com.example.hw20.data.ReminderDao
import com.example.hw20.model.Reminder

class ReminderRepository(private val reminderDao: ReminderDao) {
    val readAllData: LiveData<List<Reminder>> = reminderDao.readAllData()

    val readReminders: LiveData<List<Reminder>> = reminderDao.readReminderSeen()

    suspend fun addReminder(reminder: Reminder):Long {
        return reminderDao.addReminder(reminder)
    }

    fun updateReminder(reminder: Reminder): Int{
        return reminderDao.updateReminder(reminder)
    }

    suspend fun deleteReminder(reminder: Reminder){
        reminderDao.deleteReminder(reminder)
    }

    suspend fun deleteAllReminders(){
        reminderDao.deleteAllReminders()
    }

    fun displayAll(){
        reminderDao.readAllData()
    }


    fun updateReminderSeen(reminderId: Int, seen: Boolean){
        reminderDao.updateReminderSeen(reminderId, seen)
    }


}