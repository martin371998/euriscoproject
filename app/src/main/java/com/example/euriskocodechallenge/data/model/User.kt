package com.example.euriskocodechallenge.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    var userId: Long = 0L,
    var email: String,
    var fName: String,
    var lName: String,
    var password: String,
    var imageSrc: String?
)