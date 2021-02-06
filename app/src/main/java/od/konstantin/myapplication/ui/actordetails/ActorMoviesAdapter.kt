package od.konstantin.myapplication.ui.actordetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import od.konstantin.myapplication.R
import od.konstantin.myapplication.data.models.ActorMovie
import od.konstantin.myapplication.utils.extensions.context
import od.konstantin.myapplication.utils.extensions.setImg

class ActorMoviesAdapter(private val movieSelect: (Int) -> Unit) :
    ListAdapter<ActorMovie, ActorMoviesAdapter.ActorMovieViewHolder>(ActorMovieCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorMovieViewHolder {
        return ActorMovieViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.view_holder_actor_movie_poster, parent, false)
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

    class ActorMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val moviePoster: ImageView = itemView.findViewById(R.id.iv_movie_poster)
        private val movieTitle: TextView = itemView.findViewById(R.id.tv_movie_poster_title)

        fun bind(movie: ActorMovie) {
            moviePoster.setImg(movie.posterPicture)
            movieTitle.text = movie.title
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