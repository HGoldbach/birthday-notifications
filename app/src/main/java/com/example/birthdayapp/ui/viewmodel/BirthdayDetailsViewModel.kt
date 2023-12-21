package com.example.birthdayapp.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.birthdayapp.data.model.Person
import com.example.birthdayapp.data.repository.BirthdayRepository
import com.example.birthdayapp.ui.screens.BirthdayDetailsDestination
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate

class BirthdayDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val birthdayRepository: BirthdayRepository
) : ViewModel() {
    private val personId: Int = checkNotNull(savedStateHandle[BirthdayDetailsDestination.personIdArg])

    val uiState: StateFlow<DetailsUiState> =
        birthdayRepository.getPersonStream(personId)
            .filterNotNull()
            .map {
                DetailsUiState(it)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = DetailsUiState(Person(0,"", LocalDate.now()))
            )


    suspend fun delete() {
        birthdayRepository.deletePerson(personId)
    }

}

data class DetailsUiState(
    val person: Person
)