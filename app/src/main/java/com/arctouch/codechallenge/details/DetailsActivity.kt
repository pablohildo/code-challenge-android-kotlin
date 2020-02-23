package com.arctouch.codechallenge.details

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.palette.graphics.Palette
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.model.genresString
import com.arctouch.codechallenge.util.UrlTypes
import com.arctouch.codechallenge.util.buildUrl
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsActivity : AppCompatActivity() {

    private val viewModel by viewModel<DetailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val movieId = intent.extras?.getInt("movieId")
        movieId?.let { viewModel.setCurrentMovie(it.toLong()) }
        viewModel.currentMovie.observe(this) {
            if (it != null) {
                with(it) {
                    Log.d("AAA", it.toString())
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
                            .listener(object : RequestListener<Drawable?> {
                                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable?>?, isFirstResource: Boolean): Boolean {
                                    return false
                                }

                                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable?>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                                    if (resource is BitmapDrawable) {
                                        val palette = Palette.from(resource.bitmap).generate()
                                        val vibrant = palette.getVibrantColor(Color.WHITE)
                                        val alpha = if (vibrant != -1) vibrant else palette.getDominantColor(Color.WHITE)
                                        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), Color.WHITE, alpha)
                                        colorAnimation.duration = 1
                                        colorAnimation.addUpdateListener { animator ->
                                            rootSV.setBackgroundColor(animator.animatedValue as Int)
                                        }
                                        colorAnimation.start()
                                    }
                                    return false
                                }
                            })
                            .placeholder(R.drawable.ic_placeholder)
                            .transform(FitCenter(), RoundedCorners(20))
                            .into(posterIMG)
                }
            }
        }
    }
}