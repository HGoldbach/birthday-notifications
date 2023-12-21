package com.example.birthdayapp.ui.viewmodel

import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.ViewModel
import com.example.birthdayapp.data.model.Person
import com.example.birthdayapp.data.repository.BirthdayRepository
import com.example.birthdayapp.utils.MaskVisualTransformation
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.regex.Pattern
import kotlin.math.absoluteValue

class BirthdayEntryViewModel(private val birthdayRepository: BirthdayRepository) : ViewModel() {

    var entryUiState by mutableStateOf(EntryUiState())
        private set

    fun updateUiState(personDetails: PersonDetails) {
        entryUiState =
            EntryUiState(personDetails = personDetails, isEntryValid = validateInput(personDetails))
    }

    suspend fun saveItem() {

        if(validateInput()) {
            birthdayRepository.insertPerson(entryUiState.personDetails.toPerson())
        }

    }

    private fun validateInput(uiState: PersonDetails = entryUiState.personDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && birthday.length == 4
        }
    }

    fun maskTransformation(mask: String): MaskVisualTransformation {
        return MaskVisualTransformation(mask)
    }

}

data class EntryUiState(
    val personDetails: PersonDetails = PersonDetails(),
    val isEntryValid: Boolean = false
)

data class PersonDetails(
    val id: Int = 0,
    val name: String = "",
    val birthday: String = ""
)

fun PersonDetails.toPerson(): Person {

    val day = "${birthday[0]}${birthday[1]}".toInt()
    val month = "${birthday[2]}${birthday[3]}".toInt()

    val date = LocalDate.of(2023,month,day)

    return Person(id = id, name = name, birthday = date
)}

fun Person.toEntryUiState(isEntryValid: Boolean = false): EntryUiState = EntryUiState(
    personDetails = this.toPersonDetails(),
    isEntryValid = isEntryValid
)

fun Person.toPersonDetails(): PersonDetails = PersonDetails(
    id = id,
    name = name,
    birthday = ""
)



