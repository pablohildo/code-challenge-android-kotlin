package com.arctouch.codechallenge.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.arctouch.codechallenge.model.Genre
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.repository.MoviesRepository
import org.koin.dsl.module

val homeViewModelModule = module {
    factory { HomeViewModel(get()) }
}

class HomeViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    val upcomingMovies: LiveData<List<Movie>> = liveData {
        emit(moviesRepository.getUpcomingMovies())
    }

    val genres: LiveData<List<Genre>> = liveData {
        emit(moviesRepository.getGenres())
    }

}