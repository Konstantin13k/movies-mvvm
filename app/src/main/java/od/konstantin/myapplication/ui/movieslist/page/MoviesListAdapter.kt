package od.konstantin.myapplication.ui.movieslist.page

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import od.konstantin.myapplication.R
import od.konstantin.myapplication.data.models.MoviePoster
import od.konstantin.myapplication.databinding.ViewHolderMovieBinding
import od.konstantin.myapplication.utils.Event
import od.konstantin.myapplication.utils.extensions.context
import od.konstantin.myapplication.utils.extensions.observeEvents

class MoviesListAdapter(
    lifecycleOwner: LifecycleOwner,
    action: (MovieAction) -> Unit,
) :
    PagingDataAdapter<MoviePoster, MovieHolder>(MovieComparator) {

    private val actionEvent = MutableLiveData<Event<MovieAction>>().also {
        it.observeEvents(lifecycleOwner, action)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        getItem(position)?.let { movie ->
            holder.bind(movie)
            holder.itemView.animation =
                AnimationUtils.loadAnimation(holder.context, R.anim.alpha_recycler_view_animation)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        return MovieHolder(
            ViewHolderMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        ) {
            actionEvent.value = Event(it)
        }
    }

    sealed class MovieAction {
        data class Select(val cardView: View, val movie: MoviePoster) : MovieAction()
        data class Like(val movieId: Int, val isLiked: Boolean) : MovieAction()
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