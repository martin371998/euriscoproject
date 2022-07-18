package com.example.euriskocodechallenge.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.euriskocodechallenge.model.User
import com.example.euriskocodechallenge.data.repository.UserDatabaseRepository
import com.example.euriskocodechallenge.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userDatabaseRepository: UserDatabaseRepository
) : ViewModel() {

    private val userFinal = MutableLiveData<User>()
    private val error = MutableLiveData<String>()
    private val insertedId = MutableLiveData<Long>()

    fun fetchUser(): LiveData<User> = userFinal
    fun fetchError(): LiveData<String> = error
    fun fetchInsertedId(): LiveData<Long> = insertedId

    //Check DataStore Preferences if user is logged in
    //Fetch LoggedInUserId, then login user.
    fun isLoggedIn() {
        viewModelScope.launch {
            val isUserLoggedIn = userDatabaseRepository.isUserLoggedIn()
            if (isUserLoggedIn) {
                val loggedInUserId = userDatabaseRepository.getLoggedInUserID().first().toLong()
                val user = userDatabaseRepository.getUserById(loggedInUserId)
                user.collect {
                    if (it != null) {
                        Log.d(Constants.TAG, "User is : $it")
                        loginUser(it.email.toString(), it.password.toString())
                    }
                }
            }
        }
    }

    //Check if credentials match the info in DB
    //Set UserLoggedIn Flag in DataStore, then emit livedata of user
    fun loginUser(email: String, pass: String) {
        viewModelScope.launch {
            val user = userDatabaseRepository.loginUser(email, pass)
            user.collect {
                if (it == null) {
                    error.postValue("Invalid Credentials")
                } else {
                    val userId: Long = it.userId
                    userDatabaseRepository.setUserLoggedIn(userId)
                    userFinal.postValue(it)
                }
            }
        }
    }

    //On User-SignUp insert a new user
    fun insertUser(user: User) {
        viewModelScope.launch {
            if (user.fName.isNullOrEmpty() ||
                user.lName.isNullOrEmpty() ||
                user.email.isNullOrEmpty() ||
                user.password.isNullOrEmpty()
            ) {
                error.postValue("Input Fields cannot be empty")
            } else {
                val userId: Long = userDatabaseRepository.insertUserData(user)
                userDatabaseRepository.setUserLoggedIn(userId)
                insertedId.postValue(userId)
                userFinal.postValue(user)
            }
        }
    }


}