package com.example.birthdayapp.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.birthdayapp.BirthdayTopAppBar
import com.example.birthdayapp.ui.AppViewModelProvider
import com.example.birthdayapp.ui.navigation.NavigationDestination
import com.example.birthdayapp.ui.viewmodel.BirthdayEditViewModel
import com.example.birthdayapp.ui.viewmodel.BirthdayEntryViewModel
import kotlinx.coroutines.launch

object BirthdayEditDestination : NavigationDestination {
    override val route = "edit"
    override val titleRes = "Edit"
    const val personIdArg = "personId"
    val routeWithArgs = "$route/{$personIdArg}"
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthdayEditScreen(
    navigateUp: () -> Unit,
    navigateBack: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: BirthdayEditViewModel = viewModel(factory = AppViewModelProvider.Factory),
    viewModelEntry: BirthdayEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            BirthdayTopAppBar(
                title = BirthdayEditDestination.titleRes, 
                canNavigateBack = canNavigateBack,
                navigateUp = navigateUp
            )
        }
    ) {innerPadding ->
        EntryBody(
            entryUiState = viewModel.editUiState,
            onEntryValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.update()
                    navigateBack()
                }
            },
            modifier = Modifier.padding(innerPadding),
            viewModel = viewModelEntry
        )
        
    }
}