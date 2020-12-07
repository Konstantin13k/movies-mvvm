package od.konstantin.myapplication.moviedetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.willy.ratingbar.ScaleRatingBar
import od.konstantin.myapplication.R
import od.konstantin.myapplication.data.models.Movie
import od.konstantin.myapplication.domain.MoviesDataSource

class FragmentMoviesDetails : Fragment() {

    private lateinit var backButton: Button

    private lateinit var moviePoster: ImageView
    private lateinit var moviePg: TextView
    private lateinit var movieTitle: TextView
    private lateinit var movieTags: TextView
    private lateinit var movieRating: ScaleRatingBar
    private lateinit var movieReviews: TextView
    private lateinit var movieStoryline: TextView

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

        val moviesDataSource = MoviesDataSource()
        val movie = moviesDataSource.movies.first()
        displayMovieDetail(movie)
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
            Glide.with(context).load(movie.posterId).into(moviePoster)
            moviePg.text = context.getString(R.string.movie_pg, movie.pg)
            movieTitle.text = movie.movieTitle
            movieTags.text = movie.tags.joinToString(", ")
            movieRating.rating = movie.rating
            movieReviews.text = context.getString(R.string.movie_reviews, movie.reviews)
            movieStoryline.text = movie.storyline
            actorsAdapter.submitList(movie.actors)
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
}