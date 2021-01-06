package od.konstantin.myapplication.ui.moviedetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.willy.ratingbar.ScaleRatingBar
import od.konstantin.myapplication.R
import od.konstantin.myapplication.data.MoviesRepository
import od.konstantin.myapplication.data.models.MovieDetail
import od.konstantin.myapplication.data.remote.MoviesApi
import od.konstantin.myapplication.ui.moviedetails.adapter.ActorsListAdapter
import od.konstantin.myapplication.ui.moviedetails.adapter.ActorsListDecorator
import od.konstantin.myapplication.utils.BackdropSizes
import od.konstantin.myapplication.utils.extensions.setMovieBackdrop

class FragmentMoviesDetails : Fragment() {

    private val moviesDetailsViewModel: MoviesDetailsViewModel by viewModels {
        MoviesDetailsViewModelFactory(MoviesRepository(MoviesApi.moviesApi))
    }

    private lateinit var backButton: Button

    private lateinit var moviePoster: ImageView
    private lateinit var movieTitle: TextView
    private lateinit var movieTags: TextView
    private lateinit var movieRating: ScaleRatingBar
    private lateinit var movieReviews: TextView
    private lateinit var movieStoryline: TextView

    private lateinit var movieCastLabel: TextView

    private lateinit var movieActors: RecyclerView
    private lateinit var actorsAdapter: ActorsListAdapter

    private var backToMovieListListener: BackToMovieListListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViewsFrom(view)
        addListenersToViews()
        addAdapterToRecyclerView()

        moviesDetailsViewModel.movieDetails.observe(viewLifecycleOwner, { movie ->
            displayMovieDetail(movie)
        })

        arguments?.getInt(KEY_MOVIE_ID)?.let { movieId ->
            moviesDetailsViewModel.loadMovie(movieId)
        }

        moviesDetailsViewModel.backToMoviesList.observe(viewLifecycleOwner, { toMoviesList ->
            if (toMoviesList) {
                backToMovieListListener?.backToMovieList()
            }
        })
    }

    private fun initViewsFrom(view: View) {
        with(view) {
            backButton = findViewById(R.id.button_back)
            moviePoster = findViewById(R.id.iv_movie_poster)
            movieTitle = findViewById(R.id.tv_movie_poster_title)
            movieTags = findViewById(R.id.tv_movie_genres)
            movieRating = findViewById(R.id.rb_movie_rating)
            movieReviews = findViewById(R.id.tv_movie_reviews)
            movieStoryline = findViewById(R.id.tv_movie_storyline)
            movieActors = findViewById(R.id.rv_movie_cast)
            movieCastLabel = findViewById(R.id.tv_movie_cast_label)
        }
    }

    private fun addListenersToViews() {
        backButton.setOnClickListener {
            moviesDetailsViewModel.backButtonPressed()
        }
    }

    private fun addAdapterToRecyclerView() {
        val castImageMargin = resources.getDimension(R.dimen.cast_image_margin).toInt()
        val actorsDecorator = ActorsListDecorator(castImageMargin)
        actorsAdapter = ActorsListAdapter()
        movieActors.addItemDecoration(actorsDecorator)
        movieActors.adapter = actorsAdapter
    }

    private fun displayMovieDetail(movie: MovieDetail) {
        with(requireView()) {
            moviePoster.setMovieBackdrop(movie.backdropPicture, BackdropSizes.W780)
            movieTitle.text = movie.title
            movieTags.text = movie.genres.joinToString(", ") { it.name }
            movieRating.rating = movie.ratings / 2
//            movieReviews.text = context.getString(R.string.movie_reviews, movie.numberOfRatings)
            movieReviews.text = context.getString(R.string.movie_reviews, movie.votesCount)
            movieStoryline.text = movie.overview
            actorsAdapter.submitList(movie.actors)
            if (movie.actors.isEmpty()) {
                movieCastLabel.visibility = View.GONE
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BackToMovieListListener) {
            backToMovieListListener = context
        }
    }

    override fun onDetach() {
        backToMovieListListener = null
        super.onDetach()
    }

    interface BackToMovieListListener {
        fun backToMovieList()
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