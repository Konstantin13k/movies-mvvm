package od.konstantin.myapplication.ui.favoritemovies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.willy.ratingbar.ScaleRatingBar
import od.konstantin.myapplication.R
import od.konstantin.myapplication.data.models.FavoriteMovie
import od.konstantin.myapplication.utils.extensions.context
import od.konstantin.myapplication.utils.extensions.setImg

class FavoriteMoviesAdapter(private val selectMovie: (FavoriteMovie) -> Unit) :
    ListAdapter<FavoriteMovie, FavoriteMoviesAdapter.FavoriteMovieHolder>(MovieComparator) {

    override fun onBindViewHolder(holder: FavoriteMovieHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)

        movie?.let {
            holder.itemView.setOnClickListener {
                selectMovie(movie)
            }
            holder.itemView.animation =
                AnimationUtils.loadAnimation(holder.context, R.anim.alpha_recycler_view_animation)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieHolder {
        return FavoriteMovieHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_favorite_movie, parent, false)
        )
    }

    class FavoriteMovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val moviePoster: ImageView = itemView.findViewById(R.id.iv_favorite_movie_poster)
        private val movieLike: ImageView = itemView.findViewById(R.id.iv_movie_like)
        private val movieTitle: TextView = itemView.findViewById(R.id.tv_movie_poster_title)
        private val movieTags: TextView = itemView.findViewById(R.id.tv_movie_genres)
        private val movieRating: ScaleRatingBar = itemView.findViewById(R.id.rb_movie_rating)
        private val movieReviews: TextView = itemView.findViewById(R.id.tv_movie_reviews)
        private val movieLength: TextView = itemView.findViewById(R.id.tv_movie_length)
        private val movieStoryline: TextView = itemView.findViewById(R.id.tv_movie_storyline)

        fun bind(movie: FavoriteMovie) {
            moviePoster.setImg(movie.backdropPicture)
            movieTitle.text = movie.title
            movieTags.text = movie.genres.joinToString(", ") { it.name }
            movieRating.rating = movie.ratings
            movieReviews.text = context.getString(R.string.movie_reviews, movie.votesCount)
            movieLength.text = context.getString(R.string.movie_length, movie.runtime)
            movieStoryline.text = movie.overview
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