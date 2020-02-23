package com.arctouch.codechallenge.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.arctouch.codechallenge.R
import kotlinx.android.synthetic.main.home_activity.*
import org.koin.androidx.viewmodel.ext.android.viewModel

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

        viewModel.hasMovies.observe(this, Observer {
            // Checks if true since LiveData<Boolean> can return null
            Log.d("RRR", it.toString())
            if (it == true) progressBar.visibility = View.GONE
        })
    }
}
