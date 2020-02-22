package com.arctouch.codechallenge.repository

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.model.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MoviesDataSource(private val scope: CoroutineScope, private val tmdbApi: TmdbApi): PageKeyedDataSource<Long, Movie>() {
    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, Movie>) {
        scope.launch {
            try {
                val response = tmdbApi.upcomingMovies(1)
                when {
                    response.isSuccessful -> {
                        callback.onResult(response.body()?.results ?: listOf(), null, 2)
                    }
                }
            } catch (exception: Exception) {
                Log.e("TODO", "Add error message here")
            }
        }
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Movie>) {
        scope.launch {
            scope.launch {
                try {
                    val response = tmdbApi.upcomingMovies(params.key)
                    when {
                        response.isSuccessful -> {
                            callback.onResult(response.body()?.results ?: listOf(), params.key+1)
                        }
                    }
                } catch (exception: Exception) {
                    Log.e("TODO", "Add error message here")
                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Movie>) {
        scope.launch {
            scope.launch {
                try {
                    val response = tmdbApi.upcomingMovies(params.key)
                    when {
                        response.isSuccessful -> {
                            callback.onResult(response.body()?.results ?: listOf(), params.key-1)
                        }
                    }
                } catch (exception: Exception) {
                    Log.e("TODO", "Add error message here")
                }
            }
        }
    }

    override fun invalidate() {
        super.invalidate()
        scope.cancel()
    }
}