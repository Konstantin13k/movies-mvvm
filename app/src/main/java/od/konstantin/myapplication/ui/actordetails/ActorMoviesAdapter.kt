package od.konstantin.myapplication.ui.actordetails

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import od.konstantin.myapplication.R
import od.konstantin.myapplication.data.models.ActorMovie
import od.konstantin.myapplication.databinding.ViewHolderActorMoviePosterBinding
import od.konstantin.myapplication.utils.extensions.context
import od.konstantin.myapplication.utils.extensions.setImg

class ActorMoviesAdapter(private val movieSelect: (Int) -> Unit) :
    ListAdapter<ActorMovie, ActorMoviesAdapter.ActorMovieViewHolder>(ActorMovieCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorMovieViewHolder {
        return ActorMovieViewHolder(
            ViewHolderActorMoviePosterBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ActorMovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
        holder.itemView.animation =
            AnimationUtils.loadAnimation(holder.context, R.anim.alpha_recycler_view_animation)

        holder.itemView.setOnClickListener {
            movieSelect(movie.movieId)
        }
    }

    class ActorMovieViewHolder(private val binding: ViewHolderActorMoviePosterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: ActorMovie) {
            with(binding) {
                moviePoster.setImg(movie.posterPicture)
                movieTitle.text = movie.title
            }
        }
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