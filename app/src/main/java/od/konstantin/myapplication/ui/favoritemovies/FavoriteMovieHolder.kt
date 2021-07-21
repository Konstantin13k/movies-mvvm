package od.konstantin.myapplication.ui.favoritemovies

import androidx.recyclerview.widget.RecyclerView
import od.konstantin.myapplication.R
import od.konstantin.myapplication.data.models.FavoriteMovie
import od.konstantin.myapplication.databinding.ViewHolderFavoriteMovieBinding
import od.konstantin.myapplication.utils.extensions.context
import od.konstantin.myapplication.utils.extensions.setImg

class FavoriteMovieHolder(
    private val binding: ViewHolderFavoriteMovieBinding,
    private val action: (FavoriteMoviesAdapter.MovieAction) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    private var currentMovie: FavoriteMovie? = null

    init {
        binding.root.setOnClickListener {
            if (bindingAdapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
            val movie = currentMovie ?: return@setOnClickListener
            action(FavoriteMoviesAdapter.MovieAction.Select(binding.root, movie))
        }
        binding.unlikeButton.setOnClickListener {
            if (bindingAdapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
            val movie = currentMovie ?: return@setOnClickListener
            action(FavoriteMoviesAdapter.MovieAction.Unlike(movie.movieId))
        }
    }

    fun bind(movie: FavoriteMovie) {
        currentMovie = movie
        with(binding) {
            root.transitionName =
                context.getString(R.string.movie_poster_transition_name, movie.movieId)
            favoriteMoviePoster.setImg(movie.posterPicture)
            movieTitle.text = movie.title
            movieGenres.text = movie.genres.joinToString(", ") { it.name }
            movieRating.rating = movie.ratings
            movieReviews.text = context.getString(R.string.movie_reviews, movie.votesCount)
            movieLength.text = context.getString(R.string.movie_length, movie.runtime)
            movieStoryline.text = movie.overview
        }
    }
}