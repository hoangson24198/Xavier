package com.hoangson.xaler

import android.app.Application
import com.hoangson.xaler.core.di.networkModule
import com.hoangson.xaler.core.di.repoModule
import com.hoangson.xaler.core.di.useCaseModule
import com.hoangson.xaler.core.di.viewModelModule
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




