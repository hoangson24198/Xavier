package com.hoangson.xavier.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hoangson.xavier.data.network.CommonHeaderInterceptor
import com.hoangson.xavier.data.pref.DataStoreRepository
import com.hoangson.xavier.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import com.hoangson.xavier.core.Configuration
import com.hoangson.xavier.data.pref.UserDataStoreRepository
import com.hoangson.xavier.data.pref.operators.DataStoreOperations
import com.hoangson.xavier.data.pref.operators.UserDataStoreOperations
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(Configuration.DATASTORE_NAME)
    @Singleton
    @Provides
    fun provideDataStore(
        @ApplicationContext context: Context
    ) = context.dataStore

    @Singleton
    @Provides
    fun provideDataStoreRepository(dataStore: DataStore<Preferences>): DataStoreOperations =
        DataStoreRepository(dataStore)

    @Singleton
    @Provides
    fun provideUserDataStoreRepository(dataStore: DataStore<Preferences>): UserDataStoreOperations =
        UserDataStoreRepository(dataStore)

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
            .connectTimeout(45L, TimeUnit.SECONDS)
            .writeTimeout(45L, TimeUnit.SECONDS)
            .readTimeout(45L, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideGson() = GsonBuilder()
        .setLenient()
        .create()

    @Provides
    @Singleton
    fun provideRetroFit(okHttpClient: OkHttpClient,gson: Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl(Configuration.REMOTE_ENDPOINT)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideExoPlayer(@ApplicationContext context: Context): ExoPlayer =
        SimpleExoPlayer.Builder(context).build()
}


