package com.submission.zlux.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.submission.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(movieUseCase: MovieUseCase): ViewModel() {
    val movie = movieUseCase.getPopularMovies().asLiveData()
}