package com.example.euriskocodechallenge.utils

object Constants {

    const val TAG = "UserLogin"

    //Retrofit2 Constants
    const val BASE_URL = "https://api.nytimes.com"
    const val URL_QUERY = "/svc/mostpopular/v2/emailed/7.json?api-key=Ocqu5ePwz2Ms1SnjumZshVQkYUuMKHUA"

    //DataStore Constants
    const val USER_DATASTORE_NAME = "User DataStore"
    const val USER_LOGIN_KEY = "User Login Key"
    const val USER_ID_KEY = "User Id Key"

    //Error Fields Constants
    const val EMPTY_FIELD = "Field Cannot Be Empty!"
    const val WRONG_PASSWORD = "Wrong Password!"
    const val CONTAINS_WHITESPACE = "Field Cannot contain white spaces"
    const val INVALID_EMAIL = "Invalid Email Address!"
    const val TOO_SHORT = "Password too short! Must be greater than 6 chars"

    //Toast/SnackBar Messages
    const val USER_UPDATED = "User Updated"

    //Regex
    const val EMAIL_REGEX = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    const val WHITE_SPACE_REGEX = "\\A\\w{1,20}\\z"

}