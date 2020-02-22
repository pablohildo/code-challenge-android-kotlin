package com.arctouch.codechallenge.repository

import androidx.lifecycle.liveData
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.persistence.ChallengeDB
import com.arctouch.codechallenge.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.dsl.module
import retrofit2.Retrofit

val moviesModule = module {
    factory { MoviesRepository(get(), get()) }
}

class MoviesRepository(private val tmdbApi: TmdbApi, private val database: ChallengeDB) {
    suspend fun getGenres()  {
        withContext(Dispatchers.IO) {
            try {
                val genres = tmdbApi.genres().body()?.genres
                genres?.let { database.genres().insert(genres) }
            // It's not reasonable to display any kind of visual feedback based on any error to
            // get Genres, so it's being ignored
            } catch (ignore: Throwable) {}
        }
    }

    suspend fun getUpcomingMovies(page: Long? = null, region: String? = null) = liveData(Dispatchers.IO) {

        emit(tmdbApi.upcomingMovies(page ?: 1, region ?: Constants.DEFAULT_REGION))
    }

    suspend fun getMovie(id: Long) = tmdbApi.movie(id)

}