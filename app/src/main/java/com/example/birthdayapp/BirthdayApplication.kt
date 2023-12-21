package com.example.birthdayapp

import android.app.Application
import com.example.birthdayapp.data.AppContainer
import com.example.birthdayapp.data.AppDataContainer

class BirthdayApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}