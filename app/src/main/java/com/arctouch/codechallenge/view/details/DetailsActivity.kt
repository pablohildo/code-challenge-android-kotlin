package com.arctouch.codechallenge.view.details

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.model.genresString
import com.arctouch.codechallenge.view.ui.PaletteRequestListener
import com.arctouch.codechallenge.core.util.UrlTypes
import com.arctouch.codechallenge.core.util.buildUrl
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.android.synthetic.main.activity_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsActivity : AppCompatActivity() {

    private val viewModel by viewModel<DetailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val movieId = intent.extras?.getInt("movieId")

        if (movieId == null) displayError()
        else viewModel.setCurrentMovie(movieId.toLong())

        viewModel.currentMovie.observe(this) {
            if (it != null) {
                with(it) {
                    titleTXT.text = title
                    runtimeTXT.text = getString(R.string.minutes, runtime.toString())
                    genresTXT.text = genresString()
                    overviewTXT.text = overview
                    releaseDateTXT.text = releaseDate
                    Glide.with(this@DetailsActivity)
                            .load(backdropPath?.buildUrl(UrlTypes.BACKDROP))
                            .placeholder(R.drawable.ic_placeholder)
                            .into(backdropIMG)
                    Glide.with(this@DetailsActivity)
                            .load(posterPath?.buildUrl(UrlTypes.POSTER))
                            .listener(PaletteRequestListener(rootSV))
                            .placeholder(R.drawable.ic_placeholder)
                            .transform(FitCenter(), RoundedCorners(20))
                            .into(posterIMG)
                }
                progressPB.visibility = View.GONE
            } else {
                displayError()
            }
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun displayError() {
        Toast.makeText(this, R.string.movie_error, Toast.LENGTH_LONG).show()
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                this.finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}