package com.example.birthdayapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.birthdayapp.BirthdayTopAppBar
import com.example.birthdayapp.ui.AppViewModelProvider
import com.example.birthdayapp.ui.navigation.NavigationDestination
import com.example.birthdayapp.ui.viewmodel.BirthdayEntryViewModel
import com.example.birthdayapp.ui.viewmodel.EntryUiState
import com.example.birthdayapp.ui.viewmodel.PersonDetails
import com.example.birthdayapp.ui.viewmodel.toPerson
import kotlinx.coroutines.launch

object BirthdayEntryDestination : NavigationDestination {
    override val route = "birthday_entry"
    override val titleRes = "Entry"
}

const val DATE_MASK = "##/##"
const val DATE_LENGTH = 4

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthdayEntryScreen(
    canNavigateBack: Boolean = true,
    onNavigateUp: () -> Unit,
    navigateBack: () -> Unit,
    viewModel: BirthdayEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            BirthdayTopAppBar(
                title = BirthdayEntryDestination.titleRes,
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        EntryBody(
            entryUiState = viewModel.entryUiState,
            onEntryValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveItem()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding),
            viewModel = viewModel
        )
    }
}

@Composable
fun EntryBody(
    entryUiState: EntryUiState,
    onEntryValueChange: (PersonDetails) -> Unit,
    onSaveClick: () -> Unit,
    viewModel: BirthdayEntryViewModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(30.dp)
    ) {
        EntryInputForm(
            personDetails = entryUiState.personDetails,
            onValueChange = onEntryValueChange,
            viewModel = viewModel
        )
        Button(
            onClick = onSaveClick,
            modifier = modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(5.dp),
            enabled = entryUiState.isEntryValid
        ) {
            Text(text = "Save")
        }
    }
}

@Composable
fun EntryInputForm(
    personDetails: PersonDetails,
    modifier: Modifier = Modifier,
    onValueChange: (PersonDetails) -> Unit = {},
    viewModel: BirthdayEntryViewModel,
    enabled: Boolean = true,
) {
    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = personDetails.name,
            onValueChange = { onValueChange(personDetails.copy(name = it)) },
            label = { Text(text = "Person Name*") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            modifier = modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        Spacer(modifier = modifier.size(5.dp))
        OutlinedTextField(
            value = personDetails.birthday,
            onValueChange = {
                if(it.length <= DATE_LENGTH) {
                    onValueChange(personDetails.copy(birthday = it))
                }},
            label = { Text(text = "Person Birthday*") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = modifier
                .fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            visualTransformation = viewModel.maskTransformation(DATE_MASK)
        )
    }
}



