package com.arctouch.codechallenge.network

import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.util.Constants
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {
    factory { ApiInterceptor() }
    single { provideClient(get()) }
    single { provideOkHttpClient(get()) }
}

fun provideClient(okHttpClient: OkHttpClient): TmdbApi =
        Retrofit
                .Builder()
                .baseUrl(Constants.URL)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(TmdbApi::class.java)

fun provideOkHttpClient(apiInterceptor: ApiInterceptor): OkHttpClient =
        OkHttpClient().newBuilder().addInterceptor(apiInterceptor).build()



