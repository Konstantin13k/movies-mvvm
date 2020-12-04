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

    private var backToMovieListListener: BackToMovieListListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val backButton = view.findViewById<Button>(R.id.button_back)
        backButton.setOnClickListener {
            backToMovieListListener?.backToMovieList()
        }
        val moviesDataSource = MoviesDataSource()
        val movie = moviesDataSource.movies.first()
        displayMovieDetail(movie)
    }

    private fun displayMovieDetail(movie: Movie) {
        with(view!!) {
            val moviePoster: ImageView = findViewById(R.id.iv_movie_poster)
            val moviePg: TextView = findViewById(R.id.tv_movie_pg)
            val movieTitle: TextView = findViewById(R.id.tv_movie_poster_title)
            val movieTags: TextView = findViewById(R.id.tv_movie_tags)
            val movieRating: ScaleRatingBar = findViewById(R.id.rb_movie_rating)
            val movieReviews: TextView = findViewById(R.id.tv_movie_reviews)
            val movieStoryline: TextView = findViewById(R.id.tv_movie_storyline)
            val movieActors: RecyclerView = findViewById(R.id.rv_movie_cast)

            Glide.with(context).load(movie.posterId).into(moviePoster)
            moviePg.text = context.getString(R.string.movie_pg, movie.pg)
            movieTitle.text = movie.movieTitle
            movieTags.text = movie.tags.joinToString(", ")
            movieRating.rating = movie.rating
            movieReviews.text = context.getString(R.string.movie_reviews, movie.reviews)
            movieStoryline.text = movie.storyline

            val castImageMargin = resources.getDimension(R.dimen.cast_image_margin).toInt()
            val actorsDecorator = ActorsListDecorator(castImageMargin)
            val adapter = ActorsListAdapter()
            movieActors.addItemDecoration(actorsDecorator)
            movieActors.adapter = adapter
            adapter.submitList(movie.actors)
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