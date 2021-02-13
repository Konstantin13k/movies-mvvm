package od.konstantin.myapplication.ui.movieslist.page

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import od.konstantin.myapplication.R
import od.konstantin.myapplication.data.models.MoviePoster
import od.konstantin.myapplication.databinding.ViewHolderMovieBinding
import od.konstantin.myapplication.utils.Event
import od.konstantin.myapplication.utils.extensions.*

class MoviesListAdapter(
    lifecycleOwner: LifecycleOwner,
    action: (MovieAction) -> Unit,
) :
    PagingDataAdapter<MoviePoster, MoviesListAdapter.MovieHolder>(MovieComparator) {

    private val actionEvent = MutableLiveData<Event<MovieAction>>().also {
        it.observeEvents(lifecycleOwner, action)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        getItem(position)?.let { movie ->
            holder.bind(movie) { like ->
                actionEvent.value = Event(like)
            }
            holder.itemView.setOnClickListener {
                actionEvent.value = Event(MovieAction.Select(movie.id))
            }
            holder.itemView.animation =
                AnimationUtils.loadAnimation(holder.context, R.anim.alpha_recycler_view_animation)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        return MovieHolder(
            ViewHolderMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    sealed class MovieAction {
        data class Select(val movieId: Int) : MovieAction()
        data class Like(val movieId: Int, val isLiked: Boolean) : MovieAction()
    }

    class MovieHolder(private val binding: ViewHolderMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MoviePoster, onLikeListener: (MovieAction.Like) -> Unit) {
            with(binding) {
                moviePoster.setImg(movie.posterPicture)
                movieLike.setLike(movie.isFavorite)
                movieTitle.text = movie.title
                movieGenres.text = movie.genres.joinToString(", ") { it.name }
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
                    movieReleaseDate.setDate(releaseDate, dateFormat)
                } else {
                    movieReleaseDate.hide()
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