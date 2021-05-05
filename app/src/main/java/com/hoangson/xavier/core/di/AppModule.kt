package com.hoangson.xavier.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.hoangson.xavier.data.network.CommonHeaderInterceptor
import com.hoangson.xavier.data.pref.DataStoreOperations
import com.hoangson.xavier.data.pref.DataStoreRepository
import com.hoangson.xavier.data.remote.ApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

const val dataStoreName = "XavierDataStore"

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideDataStore(
        @ApplicationContext context: Context
    ) = context.preferencesDataStoreFile(dataStoreName)

    @Singleton
    @Provides
    fun provideDataStoreRepository(dataStore: DataStore<Preferences>): DataStoreOperations =
        DataStoreRepository(dataStore)

    @Singleton
    @Provides
    @Named("logging")
    internal fun provideHttpLoggingInterceptor(): Interceptor = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    @Named("header")
    fun provideCommonHeaderInterceptor(): Interceptor {
        return CommonHeaderInterceptor()
    }

    @Singleton
    @Provides
    fun provideOkHttp(
        @Named("logging") httpLoggingInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10L, TimeUnit.SECONDS)
            .writeTimeout(10L, TimeUnit.SECONDS)
            .readTimeout(30L, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshi() = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideRetroFit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.XAVIER_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    @Singleton
    @Provides
    fun provideDelishApi(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}


