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

    fun bind(
        movie: FavoriteMovie,
    ) {
        with(binding) {
            root.transitionName = context.getString(R.string.movie_poster_transition_name, movie.movieId)
            favoriteMoviePoster.setImg(movie.posterPicture)
            movieTitle.text = movie.title
            movieGenres.text = movie.genres.joinToString(", ") { it.name }
            movieRating.rating = movie.ratings
            movieReviews.text = context.getString(R.string.movie_reviews, movie.votesCount)
            movieLength.text = context.getString(R.string.movie_length, movie.runtime)
            movieStoryline.text = movie.overview
            unlikeButton.setOnClickListener {
                action(FavoriteMoviesAdapter.MovieAction.Unlike(movie.movieId))
            }
            itemView.setOnClickListener {
                action(FavoriteMoviesAdapter.MovieAction.Select(root, movie))
            }
        }
    }
}