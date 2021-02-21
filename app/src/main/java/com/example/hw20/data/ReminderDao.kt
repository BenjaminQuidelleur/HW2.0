package com.example.hw20.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.hw20.model.Reminder

@Dao
interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addReminder(reminder: Reminder)

    @Update
    suspend fun updateReminder(reminder: Reminder)

    @Delete
    suspend fun deleteReminder(reminder: Reminder)

    @Query("DELETE FROM reminder_table")
    suspend fun deleteAllReminders()


    @Query("SELECT * FROM reminder_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Reminder>>



}