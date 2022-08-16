package com.example.euriskocodechallenge.data.model

data class News(
    val url: String,
    val abstract: String,
    val byline: String,
    val title: String,
    val updated: String,
    val media: List<Media>
)