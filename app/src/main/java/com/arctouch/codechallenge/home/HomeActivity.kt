package com.arctouch.codechallenge.home

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.arctouch.codechallenge.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module

val homeActivityModule = module {
    factory { HomeActivity() }
}

class HomeActivity : AppCompatActivity() {
    private val viewModel by viewModel<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        viewModel.upcomingMovies.observe(this, Observer {
            it.forEach { filme ->
                Log.d("Teste", filme.title)
            }
        })
//
//        api.upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, 1, TmdbApi.DEFAULT_REGION)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe {
//                    val moviesWithGenres = it.results.map { movie ->
//                        movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
//                    }
//                    recyclerView.adapter = HomeAdapter(moviesWithGenres)
//                    progressBar.visibility = View.GONE
//                }
    }
}
