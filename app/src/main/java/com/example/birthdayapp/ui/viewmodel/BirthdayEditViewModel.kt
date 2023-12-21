package com.example.birthdayapp.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.birthdayapp.data.model.Person
import com.example.birthdayapp.data.repository.BirthdayRepository
import com.example.birthdayapp.ui.screens.BirthdayEditDestination
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

class BirthdayEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val birthdayRepository: BirthdayRepository
) : ViewModel() {
    private val personId: Int = checkNotNull(savedStateHandle[BirthdayEditDestination.personIdArg])

    var editUiState by mutableStateOf(EntryUiState())
        private set

    init {
        viewModelScope.launch {
            editUiState = birthdayRepository.getPersonStream(personId)
                .filterNotNull()
                .first()
                .toEntryUiState(true)
        }
    }

    suspend fun update() {
        if(validateInput(editUiState.personDetails)) {
            birthdayRepository.updatePerson(editUiState.personDetails.toPerson())
        }
    }

    fun updateUiState(personDetails: PersonDetails) {
        editUiState =
            EntryUiState(personDetails = personDetails, isEntryValid = validateInput(personDetails))
    }

    private fun validateInput(uiState: PersonDetails = editUiState.personDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && birthday.isNotBlank()
        }
    }

}
