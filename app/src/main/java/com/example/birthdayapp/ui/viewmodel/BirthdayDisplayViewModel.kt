package com.example.birthdayapp.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.birthdayapp.data.model.Person
import com.example.birthdayapp.data.repository.BirthdayRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.Instant
import java.time.LocalDate
import java.time.Month
import java.util.Date

class BirthdayDisplayViewModel(private val birthdayRepository: BirthdayRepository) : ViewModel() {

    val birthdayUiState: StateFlow<BirthdayUiState> =
        birthdayRepository.getAllPersonsStream().map { BirthdayUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = BirthdayUiState()
            )
}

data class BirthdayUiState(
    val personList: List<Person> = listOf()
)
