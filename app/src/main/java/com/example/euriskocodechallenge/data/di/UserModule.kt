package com.example.euriskocodechallenge.data.di

import android.content.Context
import com.example.euriskocodechallenge.data.local.datastore.UserDataStore
import com.example.euriskocodechallenge.data.local.room.UserDao
import com.example.euriskocodechallenge.data.local.room.UserDatabase
import com.example.euriskocodechallenge.data.repository.UserDatabaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object UserModule {

    @Provides
    fun provideUserDao(@ApplicationContext context: Context) : UserDao {
        return  UserDatabase.getInstance(context).userDao
    }

    @Provides
    fun provideUserDatabaseRepository (userDao: UserDao, userDataStore : UserDataStore) = UserDatabaseRepository(userDao, userDataStore)
}