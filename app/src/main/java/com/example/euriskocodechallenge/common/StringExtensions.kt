package com.example.euriskocodechallenge.common

fun String?.safe(defaultVal: String = ""): String {
    return this ?: defaultVal
}