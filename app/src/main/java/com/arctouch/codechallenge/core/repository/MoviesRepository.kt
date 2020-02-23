package com.arctouch.codechallenge.core.repository

import com.arctouch.codechallenge.core.network.api.TmdbApi
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.core.util.Constants

class MoviesRepository(private val tmdbApi: TmdbApi) {

    private suspend fun getGenres() = tmdbApi.genres().body()?.genres

    suspend fun getUpcomingMovies(page: Long? = null, region: String = Constants.DEFAULT_REGION): List<Movie>? {
        val movies = tmdbApi.upcomingMovies(page ?: 1, region)
        return (movies.body()?.results?.map { movie ->
            movie.copy(genres = getGenres()?.filter { movie.genreIds?.contains(it.id) == true })
        })
    }

    suspend fun getMovie(id: Long): Movie? = tmdbApi.movie(id).body()


}