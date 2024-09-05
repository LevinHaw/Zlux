package com.submission.favorite

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.submission.core.domain.model.Movie
import com.submission.core.presentation.MovieAdapter
import com.submission.favorite.databinding.FragmentFavoriteBinding
import com.submission.favorite.di.DaggerFavoriteComponent
import com.submission.favorite.viewModel.FavoriteViewModel
import com.submission.favorite.viewModel.ViewModelFactory
import com.submission.zlux.di.FavoriteModuleDependency
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject


class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var factory: ViewModelFactory
    private val favoriteViewModel: FavoriteViewModel by viewModels {
        factory
    }

    override fun onAttach(context: Context) {
        DaggerFavoriteComponent.builder()
            .context(requireActivity())
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireActivity().applicationContext,
                    FavoriteModuleDependency::class.java
                )
            )
            .build()
            .inject(this)
        super.onAttach(context)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFavorite.layoutManager = layoutManager
        binding.rvFavorite.setHasFixedSize(true)

        favoriteViewModel.getFavoritePopularMovie().observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                binding.rvFavorite.visibility = View.GONE
                binding.tvEmpty.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            } else {
                binding.rvFavorite.visibility = View.VISIBLE
                binding.tvEmpty.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
                setAdapter(it)
            }
        }
    }

    private fun setAdapter(it: List<Movie>) {
        val adapter = MovieAdapter(it)
        adapter.setOnItemClickCallback(object : MovieAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Movie) {
                val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailMovieActivity(
                    data.movieId
                )
                findNavController().navigate(action)
            }
        })
        binding.rvFavorite.adapter = adapter
    }


}