package com.example.euriskocodechallenge.ui.home.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.euriskocodechallenge.data.model.User
import com.example.euriskocodechallenge.data.repository.UserDatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor(
    private val userDatabaseRepository: UserDatabaseRepository,
    application: Application
) : AndroidViewModel(application) {

    private val context
        get() = getApplication<Application>()

    private val _loggedInUser = MutableLiveData<User>()
    val loggedInUser = _loggedInUser

    init {
        getLoggedInUser()
    }


    fun updateUser(fName: String, lName: String, imageString: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = userDatabaseRepository.getLoggedInUserID()
            userDatabaseRepository.getLoggedInUserID().collect {
                var updatedUser: User? = null
                var currentUser = userDatabaseRepository.getUserById(it)
                currentUser.collect { user ->
                    updatedUser = user
                }
                updatedUser?.firstName = fName
                updatedUser?.lastName = lName
                updatedUser?.imageSrc = imageString

                updatedUser?.let { updatedUser -> userDatabaseRepository.updateUser(updatedUser) }
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
                    user.firstName,
                    user.lastName,
                    newPassword,
                    user.imageSrc
                )
            )
        }
    }


}