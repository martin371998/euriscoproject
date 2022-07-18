package com.example.euriskocodechallenge.data.local.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.euriskocodechallenge.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserDataStore constructor(private val context: Context) {
    private val Context.dataStore by preferencesDataStore(name = Constants.USER_DATASTORE_NAME)

    //Mark User as Logged In and Store ID
    suspend fun loginUser(id: Long) {
        val dataStoreKey = booleanPreferencesKey(Constants.USER_LOGIN_KEY)
        val dataStoreIdKey = longPreferencesKey(Constants.USER_ID_KEY)
        context.dataStore.edit {
            it[dataStoreKey] = true
            it[dataStoreIdKey] = id
        }
    }

    //Checks if the user is logged in
    suspend fun isUserLoggedIn(): Boolean {
        val dataStoreKey = booleanPreferencesKey(Constants.USER_LOGIN_KEY)
        val loginStatus = context.dataStore.data.first()
        return loginStatus[dataStoreKey]
            ?: false //If null return false else return value in datastore
    }

    //Get Logged In User's Id
    val getUserId: Flow<Long> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.d(Constants.TAG, "Empty preferences")
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map {
            val userId = it[longPreferencesKey(Constants.USER_ID_KEY)] ?: 0
            userId
        }

    //Mark User as Logged Out
    suspend fun logOutUser() {
        val dataStoreKey = booleanPreferencesKey(Constants.USER_LOGIN_KEY)
        context.dataStore.edit {
            it[dataStoreKey] = false
            it[longPreferencesKey(Constants.USER_ID_KEY)] = 0
        }
    }
}