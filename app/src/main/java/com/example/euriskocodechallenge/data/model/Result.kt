package com.example.euriskocodechallenge.data.model

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("results")
    val news: List<News>,
)