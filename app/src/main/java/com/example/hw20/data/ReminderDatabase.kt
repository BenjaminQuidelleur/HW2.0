package com.example.hw20.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hw20.model.Reminder

@Database(entities = [Reminder::class], version = 10, exportSchema = false)
abstract class ReminderDatabase: RoomDatabase(){


    abstract fun reminderDao(): ReminderDao

    companion object{
        @Volatile
        private var INSTANCE: ReminderDatabase? = null

        fun getDatabase(context: Context):ReminderDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ReminderDatabase::class.java,
                    "reminder_database"
                ).build()
                INSTANCE = instance
                return instance


            }

        }


    }


}