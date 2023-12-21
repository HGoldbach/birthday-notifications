package com.example.birthdayapp.utils

import androidx.room.TypeConverter
import java.time.LocalDate

class Converters {
    @TypeConverter
    fun fromTimestamp(value: String): LocalDate? {
        return value.let { LocalDate.parse(it) }
    }

    @TypeConverter
    fun localDateToTimestamp(date: LocalDate?): String? {
        return date?.toString()
    }
}