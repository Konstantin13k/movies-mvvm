package od.konstantin.myapplication.ui.moviedetails

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import od.konstantin.myapplication.R
import od.konstantin.myapplication.data.models.MovieDetails
import od.konstantin.myapplication.databinding.FragmentMoviesDetailsBinding
import od.konstantin.myapplication.ui.FragmentNavigator
import od.konstantin.myapplication.ui.moviedetails.adapter.ActorsListAdapter
import od.konstantin.myapplication.ui.moviedetails.adapter.ActorsListDecorator
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
        addListenersToViews()
        addAdapterToRecyclerView()

        lifecycleScope.launchWhenStarted {
            moviesDetailsViewModel.movieDetails.collectLatest { movie ->
                movie?.let { displayMovieDetail(it) }
            }
        }

        lifecycleScope.launchWhenStarted {
            moviesDetailsViewModel.isFavoriteMovie.collectLatest { isFavorite ->
                displayIsFavoriteMovie(isFavorite)
            }
        }
    }

    private fun addListenersToViews() {
        binding.buttonBack.setOnClickListener {
            fragmentNavigator?.navigate(FragmentNavigator.Navigation.Back)
        }
    }

    private fun addAdapterToRecyclerView() {
        val castImageMargin = resources.getDimension(R.dimen.cast_image_margin).toInt()
        val actorsDecorator = ActorsListDecorator(castImageMargin)
        actorsAdapter = ActorsListAdapter { displayActorDetails(it) }
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

    private fun displayActorDetails(actorId: Int) {
        fragmentNavigator?.navigate(FragmentNavigator.Navigation.ToActorDetails(actorId), true)
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