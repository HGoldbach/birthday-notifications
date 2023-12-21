package com.example.birthdayapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.birthdayapp.data.model.Person
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {

    @Query("SELECT * FROM person ORDER BY birthday ASC")
    fun getAll(): Flow<List<Person>>

    @Query("SELECT * FROM person WHERE id = :id")
    fun getById(id: Int): Flow<Person>

    @Query( "SELECT * FROM person " +
            "WHERE strftime('%m-%d', birthday) >= strftime('%m-%d', 'now') " +
            "ORDER BY strftime('%d', birthday)" +
            "LIMIT 3"
    )
    fun getUpcoming(): Flow<List<Person>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(person: Person)

    @Update
    suspend fun update(person: Person)

    @Query("DELETE FROM person WHERE id = :id")
    suspend fun delete(id: Int)

}