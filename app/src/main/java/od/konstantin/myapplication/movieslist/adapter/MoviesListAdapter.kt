package od.konstantin.myapplication.movieslist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.getSystemService
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.willy.ratingbar.ScaleRatingBar
import od.konstantin.myapplication.R
import od.konstantin.myapplication.data.models.Movie
import od.konstantin.myapplication.data.models.MoviePoster
import od.konstantin.myapplication.movieslist.MoviesListOldAdapter
import od.konstantin.myapplication.utils.PosterSizes
import od.konstantin.myapplication.utils.extensions.context
import od.konstantin.myapplication.utils.extensions.setMoviePoster
import java.text.SimpleDateFormat
import java.util.*

class MoviesListAdapter(private val listener: OnClickListener) :
    PagingDataAdapter<MoviePoster, MoviesListAdapter.MovieHolder>(MovieComparator) {

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
        movie?.let {
            holder.itemView.setOnClickListener {
                listener.onClick(movie.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        return MovieHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie, parent, false)
        )
    }

    fun interface OnClickListener {
        fun onClick(movieId: Int)
    }

    class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val moviePoster: ImageView = itemView.findViewById(R.id.iv_movie_poster)
        private val movieTitle: TextView = itemView.findViewById(R.id.tv_movie_poster_title)
        private val movieTags: TextView = itemView.findViewById(R.id.tv_movie_tags)
        private val movieRating: ScaleRatingBar = itemView.findViewById(R.id.rb_movie_rating)
        private val movieReviews: TextView = itemView.findViewById(R.id.tv_movie_reviews)
        private val movieReleaseDate: TextView = itemView.findViewById(R.id.tv_movie_release_date)

        fun bind(movie: MoviePoster?) {
            movie?.let {
                moviePoster.setMoviePoster(movie.posterPicture, PosterSizes.W185)
                movieTitle.text = movie.title
                movieTags.text = movie.genres.joinToString(", ") { it.name }
                movieRating.rating = movie.ratings
                movieReviews.text = context.getString(R.string.movie_reviews, movie.votesCount)
                val releaseDate = movie.releaseDate
                if (releaseDate != null) {
                    val dateFormat = context.getString(R.string.movie_release_date_format)
                    movieReleaseDate.text = SimpleDateFormat(dateFormat, Locale.getDefault())
                        .format(releaseDate)
                } else {
                    movieReleaseDate.isVisible = false
                }
            }
        }
    }

    companion object {
        object MovieComparator : DiffUtil.ItemCallback<MoviePoster>() {

            override fun areItemsTheSame(oldItem: MoviePoster, newItem: MoviePoster): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MoviePoster, newItem: MoviePoster): Boolean {
                return oldItem == newItem
            }
        }
    }
}