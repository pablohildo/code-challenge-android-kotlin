package com.arctouch.codechallenge.di

import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.details.DetailsViewModel
import com.arctouch.codechallenge.home.HomeViewModel
import com.arctouch.codechallenge.network.ApiInterceptor
import com.arctouch.codechallenge.repository.MoviesRepository
import com.arctouch.codechallenge.util.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


val networkModule = module {
    factory<Interceptor> { ApiInterceptor() }
    factory {
        OkHttpClient().newBuilder().addInterceptor(get()).build()
    }
    factory<Converter.Factory> { MoshiConverterFactory.create() }
    single {
        Retrofit
                .Builder()
                .baseUrl(Constants.URL)
                .client(get())
                .addConverterFactory(get())
                .build()
                .create(TmdbApi::class.java)
    }
}

val repositoryModule = module {
    factory { MoviesRepository(get()) }
}


val viewModelModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { DetailsViewModel(get()) }
}
