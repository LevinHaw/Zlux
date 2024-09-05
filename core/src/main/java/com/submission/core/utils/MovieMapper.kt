package com.submission.core.utils

import com.submission.core.data.datasource.local.entity.MovieEntity
import com.submission.core.data.datasource.remote.response.DetailPopularMovieResponse
import com.submission.core.data.datasource.remote.response.ResultsItem
import com.submission.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object MovieMapper {

    fun mapDetailResponseToEntity(response: DetailPopularMovieResponse): MovieEntity {
        return MovieEntity(
            id = response.id ?: 0,
            title = response.title.orEmpty(),
            posterPath = response.posterPath.orEmpty(),
            overview = response.overview.orEmpty(),
            releaseDate = response.releaseDate.orEmpty(),
            popularity = response.popularity ?: 0.0,
            backdropPath = response.backdropPath.orEmpty(),
            voteAverage = response.voteAverage ?: 0.0,
            isFavorite = false,
            genres = response.genres?.joinToString(", "){ it!!.name.toString() },
            runtime = response.runtime ?: 0,
        )
    }

    fun mapResponsesToEntities(input: List<ResultsItem>): List<MovieEntity>{
        val movieList = ArrayList<MovieEntity>()
        input.map { response ->
            val movie = MovieEntity(
                id = response.id ?: 0,
                title = response.title ?: "",
                posterPath = response.posterPath ?: "",
                backdropPath = response.backdropPath ?: "",
                overview = response.overview ?: "",
                releaseDate = response.releaseDate ?: "",
                voteAverage = response.voteAverage ?: 0.0,
                popularity = response.popularity ?: 0.0,
                isFavorite = false,
                genres = response.genreIds.toString()
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                movieId = it.id,
                title = it.title,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                overview = it.overview,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
                popularity = it.popularity,
                isFavorite = it.isFavorite,
            )
        }

    fun mapEntityToDomain(input: MovieEntity): Movie =
        Movie(
            movieId = input.id,
            title = input.title,
            posterPath = input.posterPath,
            backdropPath = input.backdropPath,
            overview = input.overview,
            releaseDate = input.releaseDate,
            voteAverage = input.voteAverage,
            popularity = input.popularity,
            isFavorite = input.isFavorite,

        )

    fun mapDomainToEntity(input: Movie) = MovieEntity(
        id = input.movieId,
        title = input.title,
        posterPath = input.posterPath,
        backdropPath = input.backdropPath,
        overview = input.overview,
        releaseDate = input.releaseDate,
        voteAverage = input.voteAverage,
        popularity = input.popularity,
        isFavorite = input.isFavorite,
    )

}