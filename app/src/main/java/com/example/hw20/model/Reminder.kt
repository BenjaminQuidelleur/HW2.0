package com.example.hw20.model

import android.os.Parcelable
import java.util.*
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.hw20.data.DateTypeConvertor
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName= "reminder_table")
data class Reminder (
        @PrimaryKey(autoGenerate = true)
    val id: Int,
        val message: String,
    //val lastName: String,
    //val age: Int
    //val iconId: Int,

        val reminderDate: String,
    //val reminderTime: String,

    //val creation_time: Date,
        var reminder_seen: Boolean,
    //creator_id: Int
        var location_x: String,
        var location_y: String


): Parcelable