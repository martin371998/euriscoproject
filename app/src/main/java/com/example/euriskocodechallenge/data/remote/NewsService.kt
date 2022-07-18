package com.example.euriskocodechallenge.data.remote

import com.example.euriskocodechallenge.model.News
import com.example.euriskocodechallenge.model.Result
import com.example.euriskocodechallenge.utils.Constants
import retrofit2.Response
import retrofit2.http.GET

interface NewsService {
    @GET(Constants.URL_QUERY)
    suspend fun getAllNews() : Response<Result>


}