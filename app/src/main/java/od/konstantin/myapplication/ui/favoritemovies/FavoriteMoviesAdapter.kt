package od.konstantin.myapplication.ui.favoritemovies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import od.konstantin.myapplication.R
import od.konstantin.myapplication.data.models.FavoriteMovie
import od.konstantin.myapplication.databinding.ViewHolderFavoriteMovieBinding
import od.konstantin.myapplication.utils.extensions.context

class FavoriteMoviesAdapter(
    private val action: (MovieAction) -> Unit
) :
    ListAdapter<FavoriteMovie, FavoriteMovieHolder>(MovieComparator) {

    override fun onBindViewHolder(holder: FavoriteMovieHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
        holder.itemView.animation =
            AnimationUtils.loadAnimation(holder.context, R.anim.alpha_recycler_view_animation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieHolder {
        return FavoriteMovieHolder(
            ViewHolderFavoriteMovieBinding
                .inflate(LayoutInflater.from(parent.context), parent, false), action
        )
    }

    sealed class MovieAction {
        data class Select(val movieCardView: View, val movie: FavoriteMovie) : MovieAction()
        data class Unlike(val movieId: Int) : MovieAction()
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