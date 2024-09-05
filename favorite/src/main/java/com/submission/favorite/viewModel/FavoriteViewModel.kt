package com.submission.favorite.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.submission.core.domain.usecase.MovieUseCase

class FavoriteViewModel(private val movieUseCase: MovieUseCase): ViewModel() {
    fun getFavoritePopularMovie() = movieUseCase.getFavoriteMovie().asLiveData()
}