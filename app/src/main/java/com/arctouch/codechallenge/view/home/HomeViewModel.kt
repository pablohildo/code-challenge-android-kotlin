package com.arctouch.codechallenge.view.home

import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.core.repository.MoviesDataSource
import com.arctouch.codechallenge.core.repository.MoviesRepository

class HomeViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    val upcomingMovies: LiveData<PagedList<Movie>> by lazy {
        val config = PagedList.Config.Builder()
                .setPageSize(10)
                .setEnablePlaceholders(false)
                .build()
        pagedListBuilder(config).build()
    }
    val dataSourceLiveData = MutableLiveData<MoviesDataSource>()
    val isDataSourceEmpty: LiveData<Boolean>

    init {
        isDataSourceEmpty = dataSourceLiveData.switchMap {
            it.isDataSourceEmpty
        }
    }

    private fun pagedListBuilder(config: PagedList.Config): LivePagedListBuilder<Long, Movie> {

        val dataSourceFactory = object : DataSource.Factory<Long, Movie>() {
            override fun create(): DataSource<Long, Movie> {
                val dataSource = MoviesDataSource(viewModelScope, moviesRepository)
                dataSourceLiveData.postValue(dataSource)
                return dataSource
            }
        }
        return LivePagedListBuilder<Long, Movie>(dataSourceFactory, config)
    }
}

