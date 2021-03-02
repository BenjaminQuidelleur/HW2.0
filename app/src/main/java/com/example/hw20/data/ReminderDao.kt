package com.example.hw20.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.hw20.model.Reminder

@Dao
interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addReminder(reminder: Reminder):Long

    @Update
    fun updateReminder(reminder: Reminder): Int

    @Delete
    suspend fun deleteReminder(reminder: Reminder)

    @Query("DELETE FROM reminder_table")
    suspend fun deleteAllReminders()


    @Query("SELECT * FROM reminder_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Reminder>>


    @Query("SELECT * FROM reminder_table WHERE reminder_seen=1 ORDER BY id ASC")
    fun readReminderSeen(): LiveData<List<Reminder>>

    @Query("UPDATE reminder_table SET reminder_seen=:seen WHERE id=:reminderId")
    fun updateReminderSeen(reminderId: Int, seen: Boolean)


}