package od.konstantin.myapplication.ui.favoritemovies

import androidx.recyclerview.widget.RecyclerView
import od.konstantin.myapplication.R
import od.konstantin.myapplication.data.models.FavoriteMovie
import od.konstantin.myapplication.databinding.ViewHolderFavoriteMovieBinding
import od.konstantin.myapplication.utils.extensions.context
import od.konstantin.myapplication.utils.extensions.setImg

class FavoriteMovieHolder(private val binding: ViewHolderFavoriteMovieBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        movie: FavoriteMovie,
        onUnlikeListener: (FavoriteMoviesAdapter.MovieAction.Unlike) -> Unit
    ) {
        with(binding) {
            favoriteMoviePoster.setImg(movie.posterPicture)
            movieTitle.text = movie.title
            movieGenres.text = movie.genres.joinToString(", ") { it.name }
            movieRating.rating = movie.ratings
            movieReviews.text = context.getString(R.string.movie_reviews, movie.votesCount)
            movieLength.text = context.getString(R.string.movie_length, movie.runtime)
            movieStoryline.text = movie.overview
            unlikeButton.setOnClickListener {
                onUnlikeListener(FavoriteMoviesAdapter.MovieAction.Unlike(movie.movieId))
            }
        }
    }
}