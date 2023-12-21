package com.example.birthdayapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.birthdayapp.R
import com.example.birthdayapp.data.model.Person
import com.example.birthdayapp.ui.AppViewModelProvider
import com.example.birthdayapp.ui.navigation.NavigationDestination
import com.example.birthdayapp.ui.theme.BirthdayAppTheme
import com.example.birthdayapp.ui.viewmodel.BirthdayHomeViewModel
import java.time.LocalDate

object HomeDestination: NavigationDestination {
    override val route = "home"
    override val titleRes = "Birthday"
}

@Composable
fun HomeScreen(
    navigateToBirthdayDisplay: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BirthdayHomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ){
        HomeScreenHeader()
        Text(
            text = "Upcoming birthdays",
            style = MaterialTheme.typography.labelMedium,
            modifier = modifier
                .padding(start = 30.dp, top = 10.dp)
        )
        HomeScreenList(personList = uiState.personList)
        HomeScreenActions(navigateToBirthdayDisplay)
    }
}

@Composable
fun HomeScreenHeader(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            painter = painterResource(id = R.drawable.birthday_cake),
            contentDescription = "Cake image",
            modifier = modifier.size(100.dp)
        )
        Text(
            text = "Birthday's\n Notifications",
            modifier = modifier,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            lineHeight = 25.sp,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun HomeScreenList(
    personList: List<Person>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = 25.dp,
                bottom = 25.dp,
                end = 25.dp
            )
    ) {
        items(items = personList, key = { it.id}) { person ->
            Card(
                modifier = modifier
                    .padding(4.dp),
                shape = RoundedCornerShape(0.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                HomeScreenItem(person = person)
            }
        }
    }
}

@Composable
fun HomeScreenItem(
    person: Person,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = person.name,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "${
                if(person.birthday.dayOfMonth > 9) person.birthday.dayOfMonth
                else "0" + person.birthday.dayOfMonth
            }/${
                if(person.birthday.monthValue > 9) person.birthday.monthValue
                else "0" + person.birthday.monthValue
            }",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun HomeScreenActions(
    navigateToBirthdayDisplay: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = 5.dp,
                start = 30.dp,
                end = 30.dp,
                bottom = 30.dp
            )
    ) {
        Spacer(modifier = modifier.size(15.dp))
        Button(
            onClick = navigateToBirthdayDisplay,
            modifier = modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(topStart = 25.dp, bottomEnd = 25.dp),
            border = BorderStroke(.2.dp, MaterialTheme.colorScheme.primary)
        ) {
           Text(
               text = "See All Birthdays",
               modifier = modifier
                   .padding(10.dp),
               style = MaterialTheme.typography.bodyLarge
           )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenListPreview() {
    BirthdayAppTheme {
        HomeScreenList(personList = listOf(
            Person(0, "Hiron Goldbach", LocalDate.of(2023,1,1)),
            Person(1, "William Goldbach", LocalDate.now())
        ))
    }
}

@Preview
@Composable
fun HomeScreenHeaderPreview() {
    BirthdayAppTheme {
        HomeScreenHeader()
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenActionsPreview() {
    BirthdayAppTheme {
        HomeScreenActions({})
    }
}
