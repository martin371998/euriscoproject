package com.example.euriskocodechallenge.data.repository

import android.util.Log
import com.example.euriskocodechallenge.data.remote.NewsService
import com.example.euriskocodechallenge.utils.Constants
import javax.inject.Inject

class NewsRepository @Inject constructor(private val api: NewsService){
    suspend fun getAllNews() = api.getAllNews()
}