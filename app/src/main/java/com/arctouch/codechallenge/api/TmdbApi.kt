package com.arctouch.codechallenge.api

import com.arctouch.codechallenge.model.GenreResponse
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import com.arctouch.codechallenge.util.Constants
import org.koin.core.KoinComponent
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi : KoinComponent {

    @GET("genre/movie/list")
    suspend fun genres(): Response<GenreResponse>

    @GET("movie/upcoming")
    suspend fun upcomingMovies(
            @Query("page") page: Long,
            @Query("region") region: String = Constants.DEFAULT_REGION
    ): Response<UpcomingMoviesResponse>

    @GET("movie/{id}")
    suspend fun movie(
            @Path("id") id: Long
    ): Response<Movie>
}
