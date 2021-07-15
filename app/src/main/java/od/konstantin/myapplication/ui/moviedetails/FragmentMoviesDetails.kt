package od.konstantin.myapplication.ui.moviedetails

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale
import kotlinx.coroutines.flow.collectLatest
import od.konstantin.myapplication.R
import od.konstantin.myapplication.data.models.MovieDetails
import od.konstantin.myapplication.databinding.FragmentMoviesDetailsBinding
import od.konstantin.myapplication.ui.FragmentNavigator
import od.konstantin.myapplication.ui.FragmentNavigator.Navigation.Back
import od.konstantin.myapplication.ui.FragmentNavigator.Navigation.ToActorDetails
import od.konstantin.myapplication.ui.moviedetails.adapter.ActorsListAdapter
import od.konstantin.myapplication.ui.moviedetails.adapter.ActorsListItemDecorator
import od.konstantin.myapplication.utils.extensions.*

class FragmentMoviesDetails : Fragment(R.layout.fragment_movies_details) {

    lateinit var viewModelFactory: MoviesDetailsViewModelFactory

    private val moviesDetailsViewModel: MoviesDetailsViewModel by viewModels {
        viewModelFactory
    }

    private var actorsAdapter: ActorsListAdapter? = null

    private var fragmentNavigator: FragmentNavigator? = null

    private val binding by viewBindings { FragmentMoviesDetailsBinding.bind(it) }

    override fun onAttach(context: Context) {
        val movieDetailsComponent = DaggerMovieDetailsComponent.factory()
            .create(appComponent)

        super.onAttach(context)

        arguments?.getInt(KEY_MOVIE_ID)?.let { movieId ->
            viewModelFactory =
                movieDetailsComponent.viewModelFactoryProvider().provideViewModelFactory(movieId)
        }

        if (context is FragmentNavigator) {
            fragmentNavigator = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAnimations(view)
        initListeners()
        initActorsAdapter()
        initObservers()
    }

    private fun initListeners() {
        binding.buttonBack.setOnClickListener {
            fragmentNavigator?.navigate(Back)
        }
    }

    private fun initObservers() {
        lifecycleScope.launchWhenCreated {
            moviesDetailsViewModel.movieDetails.collectLatest { movie ->
                movie?.let { displayMovieDetail(it) }
            }
        }

        lifecycleScope.launchWhenCreated {
            moviesDetailsViewModel.isFavoriteMovie.collectLatest { isFavorite ->
                displayIsFavoriteMovie(isFavorite)
            }
        }
    }

    private fun initActorsAdapter() {
        val actorsInnerMargin = resources.getDimension(R.dimen.cast_image_margin).toInt()
        val actorsDecorator = ActorsListItemDecorator(actorsInnerMargin)
        actorsAdapter = ActorsListAdapter(::navigateToActorDetails)
        binding.movieCast.apply {
            addItemDecoration(actorsDecorator)
            adapter = actorsAdapter
        }
    }

    private fun displayMovieDetail(movie: MovieDetails) {
        with(binding) {
            moviePoster.setImg(movie.backdropPicture)
            moviePosterMask.show()
            fabLikeMovie.show()
            movieTitle.text = movie.title
            movieGenres.text = movie.genres.joinToString(", ") { it.name }
            movieRating.rating = movie.ratings
            movieReviews.text = getString(R.string.movie_reviews, movie.votesCount)
            movieStoryline.text = movie.overview
            actorsAdapter?.submitList(movie.actors)
            if (movie.actors.isEmpty()) {
                movieCastLabel.hide()
            }
        }
    }

    private fun displayIsFavoriteMovie(isFavorite: Boolean) {
        binding.fabLikeMovie.apply {
            setImageResource(if (isFavorite) R.drawable.ic_like else R.drawable.ic_favorite_movies)
            setOnClickListener {
                moviesDetailsViewModel.changeFavoriteMovie(!isFavorite)
            }
        }
    }

    private fun navigateToActorDetails(actorId: Int, actorCardView: View) {
        fragmentNavigator?.navigate(
            ToActorDetails(
                actorId,
                actorCardView
            )
        )
    }

    private fun initAnimations(view: View) {
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        val motionDuration = resources.getInteger(R.integer.motion_transition_duration).toLong()

        sharedElementEnterTransition = MaterialContainerTransform().apply {
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
    }

    override fun onDestroyView() {
        actorsAdapter = null
        super.onDestroyView()
    }

    override fun onDetach() {
        fragmentNavigator = null
        super.onDetach()
    }

    companion object {
        private const val KEY_MOVIE_ID = "movieId"

        fun newInstance(movieId: Int): FragmentMoviesDetails {
            return FragmentMoviesDetails().apply {
                arguments = bundleOf(KEY_MOVIE_ID to movieId)
            }
        }
    }
}