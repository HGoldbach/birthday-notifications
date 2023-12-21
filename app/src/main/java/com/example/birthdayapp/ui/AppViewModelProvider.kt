package com.example.birthdayapp.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.birthdayapp.BirthdayApplication
import com.example.birthdayapp.ui.viewmodel.BirthdayDetailsViewModel
import com.example.birthdayapp.ui.viewmodel.BirthdayDisplayViewModel
import com.example.birthdayapp.ui.viewmodel.BirthdayEditViewModel
import com.example.birthdayapp.ui.viewmodel.BirthdayEntryViewModel
import com.example.birthdayapp.ui.viewmodel.BirthdayHomeViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            BirthdayHomeViewModel(
                birthdayApplication().container.birthdayRepository
            )
        }
        initializer {
            BirthdayDisplayViewModel(
                birthdayApplication().container.birthdayRepository
            )
        }
        initializer {
            BirthdayEntryViewModel(
                birthdayApplication().container.birthdayRepository
            )
        }
        initializer {
            BirthdayDetailsViewModel(
                this.createSavedStateHandle(),
                birthdayApplication().container.birthdayRepository
            )
        }
        initializer {
            BirthdayEditViewModel(
                this.createSavedStateHandle(),
                birthdayApplication().container.birthdayRepository
            )
        }
    }
}

fun CreationExtras.birthdayApplication(): BirthdayApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as BirthdayApplication)