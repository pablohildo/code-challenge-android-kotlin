package com.arctouch.codechallenge.view.details

import androidx.lifecycle.*
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.core.repository.MoviesRepository

class DetailsViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {
    private val _currentMovieId = MutableLiveData<Long>()

    val currentMovie: LiveData<Movie?>

    init {
        currentMovie = Transformations.switchMap(_currentMovieId) {
            liveData {
                emit(moviesRepository.getMovie(it))
            }
        }
    }

    fun setCurrentMovie(id: Long) {
        _currentMovieId.value = id
    }
}