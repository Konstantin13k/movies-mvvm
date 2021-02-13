package od.konstantin.myapplication.ui.favoritemovies

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import od.konstantin.myapplication.R
import od.konstantin.myapplication.data.models.FavoriteMovie
import od.konstantin.myapplication.databinding.ViewHolderFavoriteMovieBinding
import od.konstantin.myapplication.utils.extensions.context
import od.konstantin.myapplication.utils.extensions.setImg

class FavoriteMoviesAdapter(
    private val action: (MovieAction) -> Unit
) :
    ListAdapter<FavoriteMovie, FavoriteMoviesAdapter.FavoriteMovieHolder>(MovieComparator) {

    override fun onBindViewHolder(holder: FavoriteMovieHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie) { unlike ->
            action(unlike)
        }
        holder.itemView.setOnClickListener {
            action(MovieAction.Select(movie.movieId))
        }
        holder.itemView.animation =
            AnimationUtils.loadAnimation(holder.context, R.anim.alpha_recycler_view_animation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieHolder {
        return FavoriteMovieHolder(
            ViewHolderFavoriteMovieBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    sealed class MovieAction {
        data class Select(val movieId: Int) : MovieAction()
        data class Unlike(val movieId: Int) : MovieAction()
    }

    class FavoriteMovieHolder(private val binding: ViewHolderFavoriteMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: FavoriteMovie, onUnlikeListener: (MovieAction.Unlike) -> Unit) {
            with(binding) {
                favoriteMoviePoster.setImg(movie.posterPicture)
                movieTitle.text = movie.title
                movieGenres.text = movie.genres.joinToString(", ") { it.name }
                movieRating.rating = movie.ratings
                movieReviews.text = context.getString(R.string.movie_reviews, movie.votesCount)
                movieLength.text = context.getString(R.string.movie_length, movie.runtime)
                movieStoryline.text = movie.overview
                unlikeButton.setOnClickListener {
                    onUnlikeListener(MovieAction.Unlike(movie.movieId))
                }
            }
        }
    }

    companion object {
        object MovieComparator : DiffUtil.ItemCallback<FavoriteMovie>() {
            override fun areItemsTheSame(oldItem: FavoriteMovie, newItem: FavoriteMovie): Boolean {
                return oldItem.movieId == newItem.movieId
            }

            override fun areContentsTheSame(
                oldItem: FavoriteMovie,
                newItem: FavoriteMovie
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}