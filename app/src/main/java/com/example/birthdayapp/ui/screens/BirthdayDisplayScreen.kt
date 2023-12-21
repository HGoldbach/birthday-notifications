package com.example.birthdayapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.birthdayapp.BirthdayTopAppBar
import com.example.birthdayapp.data.model.Person
import com.example.birthdayapp.ui.AppViewModelProvider
import com.example.birthdayapp.ui.navigation.NavigationDestination
import com.example.birthdayapp.ui.theme.BirthdayAppTheme
import com.example.birthdayapp.ui.viewmodel.BirthdayDisplayViewModel
import java.time.LocalDate

object BirthdayDisplayDestination : NavigationDestination {
    override val route = "display"
    override val titleRes = "Start"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthdayDisplayScreen(
    navigateBack: () -> Unit,
    navigateToEntry: () -> Unit,
    navigateToDetails: (Int) -> Unit,
    viewModel: BirthdayDisplayViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.birthdayUiState.collectAsState()
    Scaffold(
        topBar = {
            BirthdayTopAppBar(
                title = BirthdayDisplayDestination.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToEntry,
                shape = MaterialTheme.shapes.medium,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = ""
                )
            }
        }

    ) {innerPadding ->
        DisplayBody(
            personList = uiState.personList,
            onItemClick = navigateToDetails,
            modifier = Modifier
                .padding(innerPadding)
                .padding(bottom = 90.dp)
        )
    }
}

@Composable
private fun DisplayBody(
    personList: List<Person>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(20.dp)
    ) {
        if(personList.isEmpty()) {
            Text(
                text = "Oops! No birthday registered yet!\nTap + to add.",
                style = MaterialTheme.typography.titleLarge
            )
        } else {
            DisplayList(
                personList = personList,
                onItemClick = { onItemClick(it.id)
                }
            )
        }
    }
}

@Composable
fun DisplayList(
    personList: List<Person>,
    onItemClick: (Person) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn() {
        items(personList, key = { it.id }) { person ->
            Card(
                modifier = modifier
                    .padding(8.dp),
                elevation = CardDefaults.elevatedCardElevation(8.dp),
                shape = RoundedCornerShape(1.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )

            ) {
                DisplayItem(
                    person = person,
                    modifier = modifier
                        .clickable { onItemClick(person) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DisplayListPreview() {
    BirthdayAppTheme {
        DisplayList(personList = listOf(
            Person(id = 0, name = "Hiron", birthday = LocalDate.of(2023,9,1)),
            Person(id = 1, name = "Goldbach", birthday = LocalDate.now()),
            Person(id = 2, name = "Hiron Goldbach", birthday = LocalDate.now())
        ), onItemClick = {})
    }
}

@Composable
fun DisplayItem(
    person: Person,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable { println(person.id) },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp),
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = person.name,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "${
                        if (person.birthday.dayOfMonth > 9) person.birthday.dayOfMonth 
                        else "0" + person.birthday.dayOfMonth
                    }/${
                        if(person.birthday.monthValue > 9) person.birthday.monthValue
                        else "0" + person.birthday.monthValue
                    }",
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DisplayItemPreview() {
    BirthdayAppTheme {
        DisplayItem(person = Person(1,"Hiron", LocalDate.of(2023, 1,1)))
    }
}


@Preview(showBackground = true)
@Composable
fun DisplayBodyPreview() {
    BirthdayAppTheme {
        DisplayBody(personList = emptyList(), onItemClick = {})
    }
}

