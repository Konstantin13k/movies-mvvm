package od.konstantin.myapplication.ui.favoritemovies

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import od.konstantin.myapplication.databinding.FragmentFavoriteMoviesBinding
import od.konstantin.myapplication.ui.FragmentNavigator
import od.konstantin.myapplication.utils.extensions.appComponent
import javax.inject.Inject

class FragmentFavoriteMovies : Fragment() {

    @Inject
    lateinit var favoriteMoviesViewModelFactory: FavoriteMoviesViewModelFactory

    private val viewModel: FavoriteMoviesViewModel by viewModels {
        favoriteMoviesViewModelFactory
    }

    private var fragmentNavigator: FragmentNavigator? = null

    private var _binding: FragmentFavoriteMoviesBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        DaggerFavoriteMoviesComponent.factory().create(appComponent)
            .inject(this)

        super.onAttach(context)

        if (context is FragmentNavigator) {
            fragmentNavigator = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentFavoriteMoviesBinding.inflate(inflater, container, false)
        .also { _binding = it }.root

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

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
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