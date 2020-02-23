package com.arctouch.codechallenge.repository

import android.util.Log
import androidx.lifecycle.liveData
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.Constants
import kotlinx.coroutines.Dispatchers

class MoviesRepository(private val tmdbApi: TmdbApi) {
    suspend fun getGenres() = tmdbApi.genres().body()?.genres


    suspend fun getUpcomingMovies(page: Long? = null, region: String? = null) = liveData(Dispatchers.IO) {

        emit(tmdbApi.upcomingMovies(page ?: 1, region ?: Constants.DEFAULT_REGION))
        //         return movie?.copy(genres = genres?.filter { movie.genreIds?.contains(it.id) == true })
    }

    suspend fun getMovie(id: Long): Movie? = tmdbApi.movie(id).body()


}