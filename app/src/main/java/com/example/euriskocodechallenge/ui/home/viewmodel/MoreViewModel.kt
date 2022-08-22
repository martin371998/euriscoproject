package com.example.euriskocodechallenge.ui.home.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.euriskocodechallenge.data.model.User
import com.example.euriskocodechallenge.data.repository.UserDatabaseRepository
import com.example.euriskocodechallenge.utils.Constants
import dagger.hilt.android.internal.Contexts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor(
    private val userDatabaseRepository: UserDatabaseRepository,
    application: Application
) : AndroidViewModel(application) {

    private val context
    get()  = getApplication<Application>()

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
                        Log.d(Constants.TAG, "Updated User")
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

    suspend fun getBitmap(uri: Uri): Bitmap {
        val loader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(uri)
            .build()
        val result = (loader.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
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