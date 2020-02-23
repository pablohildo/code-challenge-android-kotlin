package com.arctouch.codechallenge.core.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.arctouch.codechallenge.model.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MoviesDataSource(private val scope: CoroutineScope, private val repository: MoviesRepository) : PageKeyedDataSource<Long, Movie>() {
    val isDataSourceEmpty = MutableLiveData<Boolean>()
    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, Movie>) {
        scope.launch {
            val upcomingMovies = repository.getUpcomingMovies()
            isDataSourceEmpty.postValue(upcomingMovies.isNullOrEmpty())
            if (!upcomingMovies.isNullOrEmpty()) callback.onResult(upcomingMovies, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Movie>) {
        scope.launch {
            val upcomingMovies = repository.getUpcomingMovies(page = params.key + 1)
            if (!upcomingMovies.isNullOrEmpty()) callback.onResult(upcomingMovies, params.key + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Movie>) {
        scope.launch {
            val upcomingMovies = repository.getUpcomingMovies(page = params.key - 1)
            if (!upcomingMovies.isNullOrEmpty()) callback.onResult(upcomingMovies, params.key - 1)
        }
    }

    override fun invalidate() {
        super.invalidate()
        scope.cancel()
    }
}