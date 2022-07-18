package com.example.euriskocodechallenge.model

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("results")
    val news: List<News>,
)

data class News(
    val url: String,
    val abstract: String,
    val byline: String,
    val title: String,
    val updated: String,
    val media: List<Media>
)