package com.arctouch.codechallenge.network

import com.arctouch.codechallenge.util.Constants
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {
    factory { KeyInterceptor() }
    single { provideClient(get()) }
    single { provideOkHttpClient(get()) }
}

fun provideClient(okHttpClient: OkHttpClient): Retrofit =
        Retrofit
                .Builder()
                .baseUrl(Constants.URL)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

fun provideOkHttpClient(keyInterceptor: KeyInterceptor): OkHttpClient =
        OkHttpClient().newBuilder().addInterceptor(keyInterceptor).build()



