package com.example.euriskocodechallenge.data.di

import android.app.Application
import com.example.euriskocodechallenge.data.local.datastore.UserDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataStoreModule {
    @Provides
    @Singleton
    fun provideUserDataStore(application: Application) : UserDataStore{
        return UserDataStore(application)
    }
}