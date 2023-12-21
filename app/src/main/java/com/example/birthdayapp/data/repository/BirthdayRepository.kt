package com.example.birthdayapp.data.repository

import com.example.birthdayapp.data.model.Person
import kotlinx.coroutines.flow.Flow

interface BirthdayRepository {

    fun getAllPersonsStream(): Flow<List<Person>>

    fun getPersonStream(id: Int): Flow<Person?>

    fun getUpcomingBirthdays(): Flow<List<Person>>

    suspend fun insertPerson(person: Person)

    suspend fun updatePerson(person: Person)

    suspend fun deletePerson(id: Int)
}