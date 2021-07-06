package od.konstantin.myapplication.ui.favoritemovies

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale
import kotlinx.coroutines.flow.collectLatest
import od.konstantin.myapplication.R
import od.konstantin.myapplication.databinding.FragmentFavoriteMoviesBinding
import od.konstantin.myapplication.ui.FragmentNavigator
import od.konstantin.myapplication.ui.moviedetails.FragmentMoviesDetails
import od.konstantin.myapplication.utils.extensions.appComponent
import od.konstantin.myapplication.utils.extensions.getColor
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
        postponeEnterTransition()
        binding.favoriteMoviesList.doOnPreDraw {
            startPostponedEnterTransition()
        }
        initAdapter()
        initListeners()
    }

    private fun initAdapter() {
        val favoriteMoviesAdapter = FavoriteMoviesAdapter { movieAction ->
            when (movieAction) {
                is FavoriteMoviesAdapter.MovieAction.Select -> showMovieDetails(
                    movieAction.movieCardView,
                    movieAction.movie.movieId
                )
                is FavoriteMoviesAdapter.MovieAction.Unlike -> viewModel.unlikeMovie(movieAction.movieId)
            }
        }
        binding.favoriteMoviesList.adapter = favoriteMoviesAdapter
        lifecycleScope.launchWhenStarted {
            viewModel.favoriteMovies.collectLatest {
                favoriteMoviesAdapter.submitList(it)
            }
        }
    }

    private fun showMovieDetails(movieCardView: View, movieId: Int) {
        val motionDuration = resources
            .getInteger(R.integer.motion_duration_to_movie_details).toLong()
        val fragment = FragmentMoviesDetails.newInstance(movieId)
        fragment.sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.main_fragment
            duration = motionDuration
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(getColor(R.color.background_color))
        }

        exitTransition = MaterialElevationScale(false).apply {
            duration = motionDuration
        }

        reenterTransition = MaterialElevationScale(true).apply {
            duration = motionDuration
        }

        val movieCardTransitionName = getString(R.string.movie_poster_details_transition_name)
        parentFragmentManager.beginTransaction()
            .addSharedElement(movieCardView, movieCardTransitionName)
            .replace(R.id.main_fragment, fragment)
            .addToBackStack(null)
            .commit()
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