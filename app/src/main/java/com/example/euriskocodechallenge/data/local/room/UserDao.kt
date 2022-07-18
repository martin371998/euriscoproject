package com.example.euriskocodechallenge.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.euriskocodechallenge.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long

    @Query("SELECT * FROM user ORDER BY userId ASC")
    fun getUsers(): LiveData<MutableList<User>>

    @Query("SELECT * FROM user WHERE email=:email AND password=:pass")
    fun loginUser(email: String, pass: String): Flow<User?>

    @Query("SELECT * FROM user WHERE userId = :id")
    fun getUserById(id : Long) : Flow<User?>

    @Update
    fun updateUser(user: User)
}