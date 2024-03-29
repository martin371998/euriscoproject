package com.example.euriskocodechallenge.ui.home.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.euriskocodechallenge.data.model.User
import com.example.euriskocodechallenge.data.repository.UserDatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor(
    private val userDatabaseRepository: UserDatabaseRepository
) : ViewModel() {

    private val _loggedInUser = MutableLiveData<User>()
    val loggedInUser = _loggedInUser

    init {
        getLoggedInUser()
    }


    fun updateUser(fName: String, lName: String, imageBitmap: Bitmap) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = userDatabaseRepository.getLoggedInUserID()
            userId.collect { id ->
                userDatabaseRepository.getUserById(id).collectLatest { user ->
                    user?.let {
                        userDatabaseRepository.updateUser(it)
                    }
                }
            }
        }
    }

    private fun getLoggedInUser() {
        viewModelScope.launch {
            if (userDatabaseRepository.isUserLoggedIn()) {
                val userId = userDatabaseRepository.getLoggedInUserID()
                userId.collect { id ->
                    userDatabaseRepository.getUserById(id).collect {
                        _loggedInUser.value = it
                    }
                }
            }
        }
    }

    fun logOutUser() {
        viewModelScope.launch {
            userDatabaseRepository.setUserLoggedOut()
        }
    }

    fun updateUserPassword(user: User, newPassword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userDatabaseRepository.updateUser(
                User(
                    user.userId,
                    user.email,
                    user.fName,
                    user.lName,
                    newPassword,
                    user.imageSrc
                )
            )
        }
    }





}