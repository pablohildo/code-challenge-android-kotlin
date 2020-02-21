package com.arctouch.codechallenge.repository

import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.util.Constants
import org.koin.dsl.module
import retrofit2.Retrofit

val moviesModule = module {
    factory { MoviesRepository(get()) }
    single<TmdbApi> { get<Retrofit>().create(TmdbApi::class.java) }
}

class MoviesRepository(private val tmdbApi: TmdbApi) {
    suspend fun getUpcomingMovies(page: Long? = null,
                                  region: String? = null) =
            tmdbApi.upcomingMovies(page ?: 1,
                    region ?: Constants.DEFAULT_REGION).results

    suspend fun getMovie(id: Long) = tmdbApi.movie(id)

    suspend fun getGenres() = tmdbApi.genres().genres
}