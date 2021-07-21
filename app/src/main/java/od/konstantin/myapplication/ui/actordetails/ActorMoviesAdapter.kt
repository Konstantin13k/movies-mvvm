package od.konstantin.myapplication.ui.actordetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import od.konstantin.myapplication.R
import od.konstantin.myapplication.data.models.ActorMovie
import od.konstantin.myapplication.databinding.ViewHolderActorMoviePosterBinding
import od.konstantin.myapplication.utils.extensions.context

class ActorMoviesAdapter(private val movieSelect: (Int, View) -> Unit) :
    ListAdapter<ActorMovie, ActorMovieViewHolder>(ActorMovieCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorMovieViewHolder {
        return ActorMovieViewHolder(
            ViewHolderActorMoviePosterBinding
                .inflate(LayoutInflater.from(parent.context), parent, false),
            movieSelect
        )
    }

    override fun onBindViewHolder(holder: ActorMovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
        playAnimation(holder)
    }

    private fun playAnimation(holder: ActorMovieViewHolder) {
        holder.itemView.animation =
            AnimationUtils.loadAnimation(holder.context, R.anim.alpha_recycler_view_animation)
    }
}

class ActorMovieCallback : DiffUtil.ItemCallback<ActorMovie>() {
    override fun areItemsTheSame(oldItem: ActorMovie, newItem: ActorMovie): Boolean {
        return oldItem.movieId == newItem.movieId
    }

    override fun areContentsTheSame(oldItem: ActorMovie, newItem: ActorMovie): Boolean {
        return oldItem == newItem
    }
}