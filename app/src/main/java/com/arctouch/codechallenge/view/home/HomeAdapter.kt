package com.arctouch.codechallenge.view.home

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.paging.PagedListAdapter
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.view.details.DetailsActivity
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.view.ui.MovieDiffCallback
import com.arctouch.codechallenge.util.UrlTypes
import com.arctouch.codechallenge.util.buildUrl
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.movie_item.view.*

class HomeAdapter : PagedListAdapter<Movie, HomeAdapter.ViewHolder>(MovieDiffCallback()) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(movie: Movie) {
            itemView.apply {
                titleTextView.text = movie.title
                genresTextView.text = movie.genres?.joinToString(separator = ", ") { it.name }
                releaseDateTextView.text = movie.releaseDate

                Glide.with(this)
                        .load(movie.posterPath?.buildUrl(UrlTypes.POSTER))
                        .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                        .into(itemView.posterImageView)

                setOnClickListener {
                    val intent = Intent(context, DetailsActivity::class.java)
                    intent.putExtra("movieId", movie.id)
                    startActivity(context, intent, null)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}
