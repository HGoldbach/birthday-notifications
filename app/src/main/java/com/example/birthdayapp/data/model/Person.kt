package com.example.birthdayapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.time.LocalDate
import java.util.Date

@Entity(tableName = "Person", indices = [Index(value = ["name"], unique = true)])
data class Person(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Int,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("birthday")
    val birthday: LocalDate
)
