package com.arctouch.codechallenge.application

import android.app.Application
import com.arctouch.codechallenge.home.homeActivityModule
import com.arctouch.codechallenge.home.homeViewModelModule
import com.arctouch.codechallenge.network.networkModule
import com.arctouch.codechallenge.repository.moviesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ChallengeApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@ChallengeApplication)
            modules(listOf(networkModule, moviesModule, homeViewModelModule, homeActivityModule))
        }
    }
}