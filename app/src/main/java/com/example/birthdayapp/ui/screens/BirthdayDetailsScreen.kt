package com.example.birthdayapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.birthdayapp.BirthdayTopAppBar
import com.example.birthdayapp.data.model.Person
import com.example.birthdayapp.ui.AppViewModelProvider
import com.example.birthdayapp.ui.navigation.NavigationDestination
import com.example.birthdayapp.ui.theme.BirthdayAppTheme
import com.example.birthdayapp.ui.viewmodel.BirthdayDetailsViewModel
import com.example.birthdayapp.ui.viewmodel.DetailsUiState
import com.example.birthdayapp.ui.viewmodel.toPerson
import kotlinx.coroutines.launch
import java.time.LocalDate

object BirthdayDetailsDestination : NavigationDestination {
    override val route = "details"
    override val titleRes = "Details"
    const val personIdArg = "personId"
    val routeWithArgs = "$route/{$personIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthdayDetailsScreen(
    canNavigateBack: Boolean = true,
    navigateUp: () -> Unit,
    navigateToEdit: (Int) -> Unit,
    viewModel: BirthdayDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            BirthdayTopAppBar(
                title = BirthdayDetailsDestination.titleRes,
                canNavigateBack = canNavigateBack,
                navigateUp = navigateUp
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToEdit(uiState.value.person.id) }
            ) {
               Icon(
                   imageVector = Icons.Default.Edit,
                   contentDescription = "Edit Person"
               )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            DetailsBody(
                detailsUiState = uiState.value,
                onDelete = {
                    coroutineScope.launch {
                        viewModel.delete()
                        navigateUp()
                    }
                }
            )
        }

    }
}

@Composable
fun DetailsBody(
    detailsUiState: DetailsUiState,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(20.dp)
    ) {
        PersonDetails(person = detailsUiState.person)
        ElevatedButton(
            onClick = onDelete,
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(topStart = 10.dp, bottomEnd = 10.dp),
            border = BorderStroke(.5.dp, MaterialTheme.colorScheme.primary)
        ) {
            Text(text = "Delete")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PersonDetailsPreview() {
    BirthdayAppTheme {
        PersonDetails(person = Person(1, "Hiron Goldbach", LocalDate.of(2023,9,1)))
    }
}

@Composable
fun PersonDetails(
    modifier: Modifier = Modifier,
    person: Person
) {
    Card(
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        PersonDetailsRow(
            label = "Name",
            personDetail = person.name
        )
        PersonDetailsRow(
            label = "Birthday",
            personDetail = "${
                if (person.birthday.dayOfMonth > 9) person.birthday.dayOfMonth
                else "0" + person.birthday.dayOfMonth
            }/${
                if(person.birthday.monthValue > 9) person.birthday.monthValue
                else "0" + person.birthday.monthValue
            }"
        )
    }
}

@Composable
fun PersonDetailsRow(
    label: String,
    personDetail: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = personDetail,
            style = MaterialTheme.typography.labelLarge
        )
    }
}