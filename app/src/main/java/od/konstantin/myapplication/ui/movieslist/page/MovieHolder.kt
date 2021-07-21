package od.konstantin.myapplication.ui.movieslist.page

import androidx.recyclerview.widget.RecyclerView
import od.konstantin.myapplication.R
import od.konstantin.myapplication.data.models.MoviePoster
import od.konstantin.myapplication.databinding.ViewHolderMovieBinding
import od.konstantin.myapplication.utils.extensions.context
import od.konstantin.myapplication.utils.extensions.setDateOrHide
import od.konstantin.myapplication.utils.extensions.setImg
import od.konstantin.myapplication.utils.extensions.setLike

class MovieHolder(
    private val binding: ViewHolderMovieBinding,
    private val action: (MoviesListAdapter.MovieAction) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    private var currentMovie: MoviePoster? = null
    private val dateFormat = context.getString(R.string.movie_release_date_format)

    init {
        binding.root.setOnClickListener {
            if (bindingAdapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
            val movie = currentMovie ?: return@setOnClickListener
            action(MoviesListAdapter.MovieAction.Select(binding.root, movie))
        }
    }

    fun bind(movie: MoviePoster) {
        currentMovie = movie
        with(binding) {
            root.transitionName = context.getString(R.string.movie_poster_transition_name, movie.id)
            moviePoster.setImg(movie.posterPicture)
            movieLike.setLike(movie.isFavorite)
            movieTitle.text = movie.title
            movieGenres.text = movie.genres
            movieRating.rating = movie.ratings
            movieReviews.text = context.getString(R.string.movie_reviews, movie.votesCount)
            movieReleaseDate.setDateOrHide(movie.releaseDate, dateFormat)
        }
    }
}