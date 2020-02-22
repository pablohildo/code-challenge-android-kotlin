package com.arctouch.codechallenge.home

import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.network.ApiInterceptor
import com.arctouch.codechallenge.network.provideClient
import com.arctouch.codechallenge.network.provideOkHttpClient
import com.arctouch.codechallenge.repository.MoviesDataSource
import com.arctouch.codechallenge.repository.MoviesRepository
import org.koin.dsl.module

val homeViewModelModule = module {
    factory { HomeViewModel(get()) }
}

class HomeViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {
//    val hasMoviesAndGenres: LiveData<Boolean>

    val upcomingMovies: LiveData<PagedList<Movie>> by lazy {
        val config = PagedList.Config.Builder()
                .setPageSize(20)
                .setEnablePlaceholders(false)
                .build()
        pagedListBuilder(config).build()
    }

    private fun pagedListBuilder(config: PagedList.Config): LivePagedListBuilder<Long, Movie> {

        val dataSourceFactory = object : DataSource.Factory<Long, Movie>() {
            override fun create(): DataSource<Long, Movie> = MoviesDataSource(viewModelScope, provideClient(provideOkHttpClient(ApiInterceptor())))
        }
        return LivePagedListBuilder<Long, Movie>(dataSourceFactory, config)
    }

//    val moviesWithGenres: LiveData<List<Movie>>
//
//
//    init {
//        hasMoviesAndGenres = MediatorLiveData<Boolean>().apply {
//            val hasMovies = Transformations.map(upcomingMovies) { it.isNotEmpty() }
//            val hasGenres = Transformations.map(genres) { it.isNotEmpty() }
//            addSource(hasMovies) {
//                value = (it ?: false) && (hasGenres.value ?: false)
//            }
//
//            addSource(hasGenres) {
//                value = (it ?: false) && (hasMovies.value ?: false)
//            }
//        }
//        moviesWithGenres = Transformations.map(hasMoviesAndGenres) {
//            upcomingMovies.value?.map { movie ->
//                movie.copy(genres = genres.value?.filter { movie.genreIds?.contains(it.id) == true })
//            }
//        }
//    }
}

