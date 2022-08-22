package com.example.euriskocodechallenge.common

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

fun String?.safe(defaultVal: String = ""): String {
    return this ?: defaultVal
}

fun String.toBitmap(): Bitmap {
    val imageBytes: ByteArray = Base64.decode(this, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
}