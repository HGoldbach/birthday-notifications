package com.example.birthdayapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.birthdayapp.data.model.Person
import com.example.birthdayapp.data.repository.BirthdayRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class BirthdayHomeViewModel(private val birthdayRepository: BirthdayRepository): ViewModel() {

    val uiState: StateFlow<HomeUiState> =
        birthdayRepository.getUpcomingBirthdays().map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = HomeUiState()
            )

}

data class HomeUiState(
    val personList: List<Person> = listOf()
)