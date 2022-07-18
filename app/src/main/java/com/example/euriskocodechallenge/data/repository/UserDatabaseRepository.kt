package com.example.euriskocodechallenge.data.repository

import com.example.euriskocodechallenge.model.User
import com.example.euriskocodechallenge.data.local.room.UserDao
import com.example.euriskocodechallenge.data.local.datastore.UserDataStore
import javax.inject.Inject

class UserDatabaseRepository @Inject constructor(
    private val userDao: UserDao,
    private val userDataStore: UserDataStore
) {
    //Room DB
    suspend fun insertUserData(user: User) = userDao.insertUser(user)
    fun getUserById(id: Long) = userDao.getUserById(id)
    fun loginUser(email: String, pass: String) = userDao.loginUser(email, pass)
    fun updateUser(user: User) = userDao.updateUser(user)

    //DataStore
    suspend fun setUserLoggedIn(id: Long) = userDataStore.loginUser(id)
    suspend fun isUserLoggedIn() = userDataStore.isUserLoggedIn()
    fun getLoggedInUserID() = userDataStore.getUserId
    suspend fun setUserLoggedOut() = userDataStore.logOutUser()
}