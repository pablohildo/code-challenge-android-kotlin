package com.arctouch.codechallenge.application

import android.app.Application
import com.arctouch.codechallenge.di.networkModule
import com.arctouch.codechallenge.di.repositoryModule
import com.arctouch.codechallenge.di.viewModelModule
import com.jakewharton.threetenabp.AndroidThreeTen
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ChallengeApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@ChallengeApplication)
            modules(listOf(networkModule, repositoryModule, viewModelModule))
        }
        AndroidThreeTen.init(this)
    }
}