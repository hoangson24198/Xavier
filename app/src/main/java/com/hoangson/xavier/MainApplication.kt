package com.hoangson.xavier

import android.app.Application
import com.hoangson.xavier.core.di.networkModule
import com.hoangson.xavier.core.di.repoModule
import com.hoangson.xavier.core.di.useCaseModule
import com.hoangson.xavier.core.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {

            androidContext(this@MainApplication)

            modules(listOf(viewModelModule, repoModule, networkModule,useCaseModule))

        }
    }

}




