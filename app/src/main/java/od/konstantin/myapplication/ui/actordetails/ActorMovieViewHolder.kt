package od.konstantin.myapplication.ui.actordetails

import androidx.recyclerview.widget.RecyclerView
import od.konstantin.myapplication.data.models.ActorMovie
import od.konstantin.myapplication.databinding.ViewHolderActorMoviePosterBinding
import od.konstantin.myapplication.utils.extensions.setImg

class ActorMovieViewHolder(private val binding: ViewHolderActorMoviePosterBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: ActorMovie) {
        with(binding) {
            root.transitionName = "actor_movie_${movie.movieId}"
            moviePoster.setImg(movie.posterPicture)
            movieTitle.text = movie.title
        }
    }
}