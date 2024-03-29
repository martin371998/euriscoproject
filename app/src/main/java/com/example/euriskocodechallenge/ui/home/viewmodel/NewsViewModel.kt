package com.example.euriskocodechallenge.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.euriskocodechallenge.data.repository.NewsRepository
import com.example.euriskocodechallenge.data.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {
    private val _response = MutableLiveData<Result>()
    val response : LiveData<Result>
    get() = _response

    private val _error = MutableLiveData<String>()
    val error : LiveData<String>
        get() = _error

    init {
        getAllNews()
    }



    private fun getAllNews() = viewModelScope.launch {
        repository.getAllNews().let {response ->
            if (response.isSuccessful){
                _response.postValue(response.body())
            } else {
                _error.postValue("${response.errorBody()}")
            }
        }
    }
}