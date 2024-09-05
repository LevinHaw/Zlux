package com.submission.core.domain.usecase

import com.submission.core.data.datasource.Resource
import com.submission.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getPopularMovies(): Flow<Resource<List<Movie>>>
    fun getDetailMovie(id: Int): Flow<Resource<Movie>>
    fun getMovieById(id: Int): Flow<Movie>
    fun getFavoriteMovie(): Flow<List<Movie>>
    fun setFavoriteMovie(movie: Movie, state: Boolean)
}