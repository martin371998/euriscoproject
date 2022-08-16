package com.example.euriskocodechallenge.data.model


import com.google.gson.annotations.SerializedName

data class Media(
    @SerializedName("media-metadata")
    val mediaMetadata: List<MediaMetadata>
)