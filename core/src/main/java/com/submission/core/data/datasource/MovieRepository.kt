package com.submission.core.data.datasource

import com.submission.core.data.datasource.local.LocalDataSource
import com.submission.core.data.datasource.remote.RemoteDataSource
import com.submission.core.data.datasource.remote.response.DetailPopularMovieResponse
import com.submission.core.data.datasource.remote.response.ResultsItem
import com.submission.core.data.datasource.remote.retrofit.ApiResponse
import com.submission.core.domain.model.Movie
import com.submission.core.domain.repository.IMovieRepository
import com.submission.core.utils.AppExecutors
import com.submission.core.utils.MovieMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): IMovieRepository{
    override fun getMovies(): Flow<Resource<List<Movie>>> =
        object: NetworkResourceHandler<List<Movie>, List<ResultsItem>>(){
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getMovie().map {
                    MovieMapper.mapEntitiesToDomain(it)
                }
            }

            @Suppress("UNCHECKED_CAST")
            override suspend fun createCall(): Flow<ApiResponse<List<ResultsItem>>> =
                remoteDataSource.getPopularMovies() as Flow<ApiResponse<List<ResultsItem>>>

            override suspend fun saveCallResult(data: List<ResultsItem>) {
                val movieList = MovieMapper.mapResponsesToEntities(data)
                localDataSource.insertMovie(movieList)
            }

            override fun shouldFetch(data: List<Movie>?): Boolean =
                data.isNullOrEmpty()

        }.asFlow()

    override fun getDetailMovie(id: Int): Flow<Resource<Movie>> =
        object : NetworkResourceHandler<Movie, DetailPopularMovieResponse>(){
            override fun loadFromDB(): Flow<Movie> {
                return localDataSource.getMovieById(id).map {
                    MovieMapper.mapEntityToDomain(it)
                }
            }

            override suspend fun createCall(): Flow<ApiResponse<DetailPopularMovieResponse>> =
                remoteDataSource.getDetailMovie(id)

            override suspend fun saveCallResult(data: DetailPopularMovieResponse) {
                val movieEntity = MovieMapper.mapDetailResponseToEntity(data)
                localDataSource.insertMovie(listOf(movieEntity))
            }

            override fun shouldFetch(data: Movie?): Boolean =
                data == null

        }.asFlow()

    override fun getMovieById(id: Int): Flow<Movie> {
        return localDataSource.getMovieById(id).map {
            MovieMapper.mapEntityToDomain(it)
        }
    }

    override fun getFavoriteMovie(): Flow<List<Movie>> {
        return localDataSource.getFavoriteMovie().map {
            MovieMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteMovie(movie: Movie, state: Boolean) {
        val movieEntity = MovieMapper.mapDomainToEntity(movie)
        appExecutors.diskIO().execute {
            localDataSource.setFavoriteMovie(movieEntity, state)
        }
    }
}