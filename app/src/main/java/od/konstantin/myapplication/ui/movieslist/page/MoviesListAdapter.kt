package od.konstantin.myapplication.ui.movieslist.page

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.willy.ratingbar.ScaleRatingBar
import od.konstantin.myapplication.R
import od.konstantin.myapplication.data.models.MoviePoster
import od.konstantin.myapplication.utils.Event
import od.konstantin.myapplication.utils.extensions.context
import od.konstantin.myapplication.utils.extensions.observeEvents
import od.konstantin.myapplication.utils.extensions.setImg
import od.konstantin.myapplication.utils.extensions.setLike
import java.text.SimpleDateFormat
import java.util.*

class MoviesListAdapter(
    lifecycleOwner: LifecycleOwner,
    action: (MovieAction) -> Unit,
) :
    PagingDataAdapter<MoviePoster, MoviesListAdapter.MovieHolder>(MovieComparator) {

    private val actionEvent = MutableLiveData<Event<MovieAction>>().also {
        it.observeEvents(lifecycleOwner, action)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie) { like ->
            actionEvent.value = Event(like)
        }
        movie?.let {
            holder.itemView.setOnClickListener {
                actionEvent.value = Event(MovieAction.Select(movie.id))
            }
            holder.itemView.animation =
                AnimationUtils.loadAnimation(holder.context, R.anim.alpha_recycler_view_animation)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        return MovieHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie, parent, false)
        )
    }

    sealed class MovieAction {
        data class Select(val movieId: Int) : MovieAction()
        data class Like(val movieId: Int, val isLiked: Boolean) : MovieAction()
    }

    class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val moviePoster: ImageView = itemView.findViewById(R.id.iv_movie_poster)
        private val movieLike: ImageView = itemView.findViewById(R.id.iv_movie_like)
        private val movieTitle: TextView = itemView.findViewById(R.id.tv_movie_poster_title)
        private val movieTags: TextView = itemView.findViewById(R.id.tv_movie_genres)
        private val movieRating: ScaleRatingBar = itemView.findViewById(R.id.rb_movie_rating)
        private val movieReviews: TextView = itemView.findViewById(R.id.tv_movie_reviews)
        private val movieReleaseDate: TextView = itemView.findViewById(R.id.tv_movie_release_date)

        fun bind(movie: MoviePoster?, onLikeListener: (MovieAction.Like) -> Unit) {
            movie?.let {
                moviePoster.setImg(movie.posterPicture)
                movieLike.setLike(movie.isFavorite)
                movieTitle.text = movie.title
                movieTags.text = movie.genres.joinToString(", ") { it.name }
                movieRating.rating = movie.ratings
                movieReviews.text = context.getString(R.string.movie_reviews, movie.votesCount)
                movieLike.setOnClickListener {
                    movie.isFavorite = !movie.isFavorite
                    onLikeListener(MovieAction.Like(movie.id, movie.isFavorite))
                    movieLike.setLike(movie.isFavorite)
                }
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