package com.example.nytchallenge.data
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nytchallenge.util.constants.DATABASE_TABLE

@Entity(tableName = DATABASE_TABLE)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val task: String
)
