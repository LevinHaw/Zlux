package com.submission.core.domain.usecase

import com.submission.core.data.datasource.MovieRepository
import com.submission.core.data.datasource.Resource
import com.submission.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteractor @Inject constructor(
    private val movieRepository: MovieRepository
): MovieUseCase {
    override fun getPopularMovies() = movieRepository.getMovies()

    override fun getDetailMovie(id: Int): Flow<Resource<Movie>> = movieRepository.getDetailMovie(id)

    override fun getMovieById(id: Int): Flow<Movie> = movieRepository.getMovieById(id)

    override fun getFavoriteMovie() = movieRepository.getFavoriteMovie()

    override fun setFavoriteMovie(movie: Movie, state: Boolean) = movieRepository.setFavoriteMovie(movie, state)
}