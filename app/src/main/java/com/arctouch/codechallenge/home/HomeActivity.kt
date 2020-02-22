package com.arctouch.codechallenge.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.arctouch.codechallenge.R
import kotlinx.android.synthetic.main.home_activity.*
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
        val adapter = HomeAdapter()
        recyclerView.adapter = adapter
        viewModel.upcomingMovies.observe(this, Observer {
            adapter.submitList(it)
        })
//        viewModel.moviesWithGenres.observe(this, Observer {
//            if (it != null) recyclerView.adapter = HomeAdapter(it)
//        })
//
//        viewModel.hasMoviesAndGenres.observe(this, Observer {
//            progressBar.visibility = View.GONE
//        })
    }
}
