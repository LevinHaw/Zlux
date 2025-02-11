package com.submission.favorite.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.submission.core.domain.usecase.MovieUseCase
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val movieUseCase: MovieUseCase):
ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> FavoriteViewModel(
                movieUseCase
            ) as T
            else -> throw IllegalArgumentException("Unknown Viewmodel class ${modelClass.name}")
        }
}