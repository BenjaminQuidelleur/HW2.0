package com.example.hw20.data

import androidx.room.TypeConverter
import java.sql.Date


class DateTypeConvertor {
    @TypeConverter
    fun toDate(dateLong:Long):Date {
        return Date(dateLong)
    }

    @TypeConverter
    fun fromDate(date: Date):Long{
        return date.time;
    }
}