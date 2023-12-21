package com.example.birthdayapp.data.repository

import com.example.birthdayapp.data.dao.PersonDao
import com.example.birthdayapp.data.model.Person
import kotlinx.coroutines.flow.Flow

class OfflineBirthdayRepository(private val personDao: PersonDao) : BirthdayRepository {
    override fun getAllPersonsStream(): Flow<List<Person>> {
        return personDao.getAll()
    }

    override fun getPersonStream(id: Int): Flow<Person?> {
        return personDao.getById(id)
    }

    override fun getUpcomingBirthdays(): Flow<List<Person>> {
        return personDao.getUpcoming()
    }

    override suspend fun insertPerson(person: Person) {
        personDao.insert(person)
    }

    override suspend fun updatePerson(person: Person) {
        personDao.update(person)
    }

    override suspend fun deletePerson(id: Int) {
        personDao.delete(id)
    }
}