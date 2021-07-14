package od.konstantin.myapplication.ui.favoritemovies

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.transition.MaterialElevationScale
import kotlinx.coroutines.flow.collectLatest
import od.konstantin.myapplication.R
import od.konstantin.myapplication.databinding.FragmentFavoriteMoviesBinding
import od.konstantin.myapplication.ui.FragmentNavigator
import od.konstantin.myapplication.ui.FragmentNavigator.Navigation.ToMovieDetails
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
        initAnimations(view)
        initListeners()
        initFavoriteMoviesAdapter()
    }

    private fun initListeners() {
        binding.buttonBack.setOnClickListener {
            fragmentNavigator?.navigate(FragmentNavigator.Navigation.Back)
        }
    }

    private fun initFavoriteMoviesAdapter() {
        val favoriteMoviesAdapter = FavoriteMoviesAdapter { movieAction ->
            when (movieAction) {
                is FavoriteMoviesAdapter.MovieAction.Select -> navigateToMovieDetails(
                    movieAction.movie.movieId,
                    movieAction.movieCardView
                )
                is FavoriteMoviesAdapter.MovieAction.Unlike -> viewModel.unlikeMovie(movieAction.movieId)
            }
        }
        binding.favoriteMoviesList.setHasFixedSize(true)
        binding.favoriteMoviesList.adapter = favoriteMoviesAdapter
        lifecycleScope.launchWhenStarted {
            viewModel.favoriteMovies.collectLatest {
                favoriteMoviesAdapter.submitList(it)
            }
        }
    }

    private fun navigateToMovieDetails(movieId: Int, movieCardView: View) {
        fragmentNavigator?.navigate(
            ToMovieDetails(
                movieId,
                movieCardView
            )
        )
    }

    private fun initAnimations(view: View) {
        postponeEnterTransition()
        view.doOnPreDraw {
            startPostponedEnterTransition()
        }

        val motionDuration = resources.getInteger(R.integer.motion_transition_duration).toLong()

        enterTransition = MaterialElevationScale(true).apply {
            duration = motionDuration
        }

        exitTransition = MaterialElevationScale(false).apply {
            duration = motionDuration
        }

        reenterTransition = MaterialElevationScale(true).apply {
            duration = motionDuration
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