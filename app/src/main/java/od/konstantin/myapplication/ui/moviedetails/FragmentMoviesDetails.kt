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
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.willy.ratingbar.ScaleRatingBar
import od.konstantin.myapplication.MyApplication
import od.konstantin.myapplication.R
import od.konstantin.myapplication.data.models.MovieDetail
import od.konstantin.myapplication.ui.moviedetails.adapter.ActorsListAdapter
import od.konstantin.myapplication.ui.moviedetails.adapter.ActorsListDecorator
import od.konstantin.myapplication.utils.extensions.setImg
import javax.inject.Inject

class FragmentMoviesDetails : Fragment() {

    @Inject
    lateinit var viewModelFactory: MoviesDetailsViewModelFactory

    private val moviesDetailsViewModel: MoviesDetailsViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var backButton: Button

    private lateinit var moviePoster: ImageView
    private lateinit var moviePosterMask: View
    private lateinit var movieTitle: TextView
    private lateinit var movieTags: TextView
    private lateinit var movieRating: ScaleRatingBar
    private lateinit var movieReviews: TextView
    private lateinit var movieStoryline: TextView

    private lateinit var movieCastLabel: TextView

    private lateinit var movieActors: RecyclerView
    private lateinit var actorsAdapter: ActorsListAdapter

    private var backToMovieListListener: BackToMovieListListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as MyApplication).appComponent.movieDetailsComponent()
            .create().inject(this)

        if (context is BackToMovieListListener) {
            backToMovieListListener = context
        }
    }

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

        arguments?.getInt(KEY_MOVIE_ID)?.let { movieId ->
            moviesDetailsViewModel.loadMovie(movieId).observe(viewLifecycleOwner, { movie ->
                movie?.let { displayMovieDetail(it) }
            })
        }
    }

    private fun initViewsFrom(view: View) {
        with(view) {
            backButton = findViewById(R.id.button_back)
            moviePoster = findViewById(R.id.iv_movie_poster)
            moviePosterMask = findViewById(R.id.movie_poster_mask)
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
            requireActivity().onBackPressed()
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
            moviePoster.setImg(movie.backdropPicture)
            moviePosterMask.isVisible = true
            movieTitle.text = movie.title
            movieTags.text = movie.genres.joinToString(", ") { it.name }
            movieRating.rating = movie.ratings
            movieReviews.text = context.getString(R.string.movie_reviews, movie.votesCount)
            movieStoryline.text = movie.overview
            actorsAdapter.submitList(movie.actors)
            if (movie.actors.isEmpty()) {
                movieCastLabel.visibility = View.GONE
            }
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