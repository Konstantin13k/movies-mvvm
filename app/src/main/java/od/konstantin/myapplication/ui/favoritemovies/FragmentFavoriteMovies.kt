package od.konstantin.myapplication.ui.favoritemovies

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import od.konstantin.myapplication.R
import od.konstantin.myapplication.databinding.FragmentFavoriteMoviesBinding
import od.konstantin.myapplication.ui.FragmentNavigator
import od.konstantin.myapplication.utils.extensions.appComponent
import od.konstantin.myapplication.utils.extensions.viewBindings
import javax.inject.Inject

class FragmentFavoriteMovies : Fragment(R.layout.fragment_favorite_movies) {

    @Inject
    lateinit var favoriteMoviesViewModelFactory: FavoriteMoviesViewModelFactory

    private val viewModel: FavoriteMoviesViewModel by viewModels {
        favoriteMoviesViewModelFactory
    }

    private var fragmentNavigator: FragmentNavigator? = null

    private val binding by viewBindings { FragmentFavoriteMoviesBinding.bind(it) }

    override fun onAttach(context: Context) {
        DaggerFavoriteMoviesComponent.factory().create(appComponent)
            .inject(this)

        super.onAttach(context)

        if (context is FragmentNavigator) {
            fragmentNavigator = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initListeners()
    }

    private fun initAdapter() {
        val favoriteMoviesAdapter = FavoriteMoviesAdapter { movieAction ->
            when (movieAction) {
                is FavoriteMoviesAdapter.MovieAction.Select -> fragmentNavigator?.navigate(
                    FragmentNavigator.Navigation.ToMovieDetails(movieAction.movieId),
                    addToBackStack = true
                )
                is FavoriteMoviesAdapter.MovieAction.Unlike -> viewModel.unlikeMovie(movieAction.movieId)
            }
        }
        binding.favoriteMoviesList.adapter = favoriteMoviesAdapter

        lifecycleScope.launchWhenCreated {
            viewModel.favoriteMovies.collectLatest {
                favoriteMoviesAdapter.submitList(it)
            }
        }
    }

    private fun initListeners() {
        binding.buttonBack.setOnClickListener {
            fragmentNavigator?.navigate(FragmentNavigator.Navigation.Back)
        }
    }

    override fun onDetach() {
        fragmentNavigator = null
        super.onDetach()
    }

    companion object {
        fun newInstance(): FragmentFavoriteMovies {
            return FragmentFavoriteMovies()
        }
    }
}