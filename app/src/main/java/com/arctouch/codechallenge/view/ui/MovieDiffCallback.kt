package com.arctouch.codechallenge.view.ui

import androidx.recyclerview.widget.DiffUtil
import com.arctouch.codechallenge.model.Movie

class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem.id == newItem.id

    // It's highly probable that two movies with the same release date and the same title are pretty
    // much the same movie

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
            oldItem.title == newItem.title && oldItem.releaseDate == newItem.releaseDate
}
