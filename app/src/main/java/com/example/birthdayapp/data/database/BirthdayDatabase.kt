package com.example.birthdayapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.birthdayapp.data.dao.PersonDao
import com.example.birthdayapp.data.model.Person
import com.example.birthdayapp.utils.Converters

@Database(entities = [Person::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class BirthdayDatabase : RoomDatabase() {

    abstract fun PersonDao(): PersonDao

    companion object {
        @Volatile
        private var Instance : BirthdayDatabase? = null

        fun getDatabase(context: Context): BirthdayDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, BirthdayDatabase::class.java, "birthday_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}