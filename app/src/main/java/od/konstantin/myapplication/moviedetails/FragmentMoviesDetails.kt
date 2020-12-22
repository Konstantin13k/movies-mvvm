package od.konstantin.myapplication.moviedetails

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
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.willy.ratingbar.ScaleRatingBar
import kotlinx.coroutines.*
import od.konstantin.myapplication.R
import od.konstantin.myapplication.data.models.Movie
import od.konstantin.myapplication.domain.MoviesDataSource

class FragmentMoviesDetails : Fragment() {

    private val fragmentScope = CoroutineScope(Dispatchers.Default + Job())

    private lateinit var backButton: Button

    private lateinit var moviePoster: ImageView
    private lateinit var moviePg: TextView
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

        arguments?.getInt(KEY_MOVIE_ID)?.let { movieId ->
            val moviesDataSource = MoviesDataSource(requireContext().applicationContext)
            fragmentScope.launch {
                moviesDataSource.getMovie(movieId)?.let { movie ->
                    withContext(Dispatchers.Main) {
                        displayMovieDetail(movie)
                    }
                }
            }
        }
    }

    private fun initViewsFrom(view: View) {
        with(view) {
            backButton = findViewById(R.id.button_back)
            moviePoster = findViewById(R.id.iv_movie_poster)
            moviePg = findViewById(R.id.tv_movie_pg)
            movieTitle = findViewById(R.id.tv_movie_poster_title)
            movieTags = findViewById(R.id.tv_movie_tags)
            movieRating = findViewById(R.id.rb_movie_rating)
            movieReviews = findViewById(R.id.tv_movie_reviews)
            movieStoryline = findViewById(R.id.tv_movie_storyline)
            movieActors = findViewById(R.id.rv_movie_cast)
            movieCastLabel = findViewById(R.id.tv_movie_cast_label)
        }
    }

    private fun addListenersToViews() {
        backButton.setOnClickListener {
            backToMovieListListener?.backToMovieList()
        }
    }

    private fun addAdapterToRecyclerView() {
        val castImageMargin = resources.getDimension(R.dimen.cast_image_margin).toInt()
        val actorsDecorator = ActorsListDecorator(castImageMargin)
        actorsAdapter = ActorsListAdapter()
        movieActors.addItemDecoration(actorsDecorator)
        movieActors.adapter = actorsAdapter
    }

    private fun displayMovieDetail(movie: Movie) {
        with(view!!) {
            Glide.with(context).load(movie.backdrop).into(moviePoster)
            moviePg.text = context.getString(R.string.movie_pg, movie.minimumAge)
            movieTitle.text = movie.title
            movieTags.text = movie.genres.joinToString(", ") { it.name }
            movieRating.rating = movie.ratings / 2
            movieReviews.text = context.getString(R.string.movie_reviews, movie.numberOfRatings)
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