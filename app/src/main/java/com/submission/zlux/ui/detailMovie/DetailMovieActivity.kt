package com.submission.zlux.ui.detailMovie

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.submission.core.data.datasource.Resource
import com.submission.core.domain.model.Movie
import com.submission.zlux.R
import com.submission.zlux.databinding.ActivityDetailMovieBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMovieBinding
    private val args: DetailMovieActivityArgs by navArgs()
    private val detailMovieViewModel: DetailMovieViewModel by viewModels()
    private var statusFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val movieId: Int = args.movieId

        detailMovieViewModel.getDetailPopularMovie(movieId).observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    resource.data?.let { setData(it) }
                }

                is Resource.Error ->
                    binding.progressBar.visibility = View.GONE
            }
        }

        binding.fabBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setData(movie: Movie) {
        binding.apply {
            Glide.with(this@DetailMovieActivity)
                .load(movie.fullPosterPath)
                .into(ivPoster)
            Glide.with(this@DetailMovieActivity)
                .load(movie.fullBackdropPath)
                .centerCrop()
                .into(ivBackposter)
            tvTitleMovie.text = movie.title
            tvOverview.text = movie.overview
            tvReleaseDate.text = "Release : " + movie.releaseDate
            tvView.text = movie.popularity.toString()
            tvRating.text = movie.voteAverage.toString()

            detailMovieViewModel.getPopularMovieById(movie.movieId).observe(this@DetailMovieActivity) { movieEntity ->
                statusFavorite = movieEntity.isFavorite
                setStatusFavorite(statusFavorite)
            }

            fabFavorite.setOnClickListener {
                statusFavorite = !statusFavorite
                detailMovieViewModel.setFavoriteMovie(movie, statusFavorite)
                setStatusFavorite(statusFavorite)
            }
        }
    }




    private fun setStatusFavorite(isFavorite: Boolean) {
        val favoriteIcon = if (isFavorite) {
            R.drawable.ic_favorite_filled
        } else {
            R.drawable.ic_favorite
        }
        binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(this, favoriteIcon))
        binding.fabFavorite.imageTintList = ContextCompat.getColorStateList(this, android.R.color.holo_red_dark)
    }
}