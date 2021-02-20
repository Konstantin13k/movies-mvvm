package od.konstantin.myapplication.ui.movieslist.page

import androidx.recyclerview.widget.RecyclerView
import od.konstantin.myapplication.R
import od.konstantin.myapplication.data.models.MoviePoster
import od.konstantin.myapplication.databinding.ViewHolderMovieBinding
import od.konstantin.myapplication.utils.extensions.*

class MovieHolder(
    private val binding: ViewHolderMovieBinding,
    private val action: (MoviesListAdapter.MovieAction) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: MoviePoster) {
        with(binding) {
            root.transitionName = context.getString(R.string.movie_poster_transition_name, movie.id)
            moviePoster.setImg(movie.posterPicture)
            movieLike.setLike(movie.isFavorite)
            movieTitle.text = movie.title
            movieGenres.text = movie.genres.joinToString(", ") { it.name }
            movieRating.rating = movie.ratings
            movieReviews.text = context.getString(R.string.movie_reviews, movie.votesCount)
            movieLike.setOnClickListener {
                movie.isFavorite = !movie.isFavorite
                action(MoviesListAdapter.MovieAction.Like(movie.id, movie.isFavorite))
                movieLike.setLike(movie.isFavorite)
            }
            itemView.setOnClickListener {
                action(MoviesListAdapter.MovieAction.Select(root, movie))
            }
            val releaseDate = movie.releaseDate
            if (releaseDate != null) {
                val dateFormat = context.getString(R.string.movie_release_date_format)
                movieReleaseDate.setDate(releaseDate, dateFormat)
            } else {
                movieReleaseDate.hide()
            }
        }
    }
}