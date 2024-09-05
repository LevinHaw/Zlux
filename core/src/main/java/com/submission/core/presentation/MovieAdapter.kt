package com.submission.core.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.submission.core.R
import com.submission.core.databinding.ItemMovieBinding
import com.submission.core.domain.model.Movie

class MovieAdapter(
    private var listMovie: List<Movie>
): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = listMovie[position]
        holder.bind(movie)
        holder.binding.cardView.setOnClickListener {
            onItemClickCallback.onItemClicked(listMovie[holder.adapterPosition])
        }

    }

    override fun getItemCount(): Int = listMovie.size

    class MovieViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            Glide.with(binding.root.context)
                .load(movie.fullPosterPath)
                .into(binding.ivPoster)

            binding.tvTitleMovie.text = movie.title
            binding.tvOverview.text = movie.overview
            binding.tvDate.text = movie.releaseDate
            binding.tvViews.text = movie.popularity.toString()
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Movie)
    }


}