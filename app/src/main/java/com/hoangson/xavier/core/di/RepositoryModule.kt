package com.hoangson.xavier.core.di

import com.hoangson.xavier.data.pref.UserDataStoreRepository
import com.hoangson.xavier.data.remote.ApiService
import com.hoangson.xavier.data.remote.AuthRepository
import com.hoangson.xavier.data.remote.operators.AuthOperations
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideAuthRepository(
        apiService: ApiService
    ) : AuthOperations = AuthRepository(apiService)
}