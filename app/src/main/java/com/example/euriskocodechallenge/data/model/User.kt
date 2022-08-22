package com.example.euriskocodechallenge.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    var userId: Long = 0L,
    var email: String,
    var firstName: String,
    var lastName: String,
    var password: String,
    var imageSrc: String?
)