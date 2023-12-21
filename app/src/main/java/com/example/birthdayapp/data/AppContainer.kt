package com.example.birthdayapp.data

import android.content.Context
import com.example.birthdayapp.data.database.BirthdayDatabase
import com.example.birthdayapp.data.repository.BirthdayRepository
import com.example.birthdayapp.data.repository.OfflineBirthdayRepository

interface AppContainer {
    val birthdayRepository: BirthdayRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val birthdayRepository: BirthdayRepository by lazy {
        OfflineBirthdayRepository(BirthdayDatabase.getDatabase(context).PersonDao())
    }
}