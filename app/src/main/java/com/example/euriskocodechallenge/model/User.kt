package com.example.euriskocodechallenge.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: Long = 0L,
    val email: String,
    val fName: String,
    val lName: String,
    val password: String,
    val imageSrc: Bitmap
)