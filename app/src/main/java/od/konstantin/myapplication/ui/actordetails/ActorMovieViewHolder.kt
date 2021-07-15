package od.konstantin.myapplication.ui.actordetails

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import od.konstantin.myapplication.R
import od.konstantin.myapplication.data.models.ActorMovie
import od.konstantin.myapplication.databinding.ViewHolderActorMoviePosterBinding
import od.konstantin.myapplication.utils.extensions.context
import od.konstantin.myapplication.utils.extensions.setImg

class ActorMovieViewHolder(
    private val binding: ViewHolderActorMoviePosterBinding,
    private val movieSelect: (Int, View) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    private var currentMovie: ActorMovie? = null

    init {
        binding.root.setOnClickListener {
            val movie = currentMovie ?: return@setOnClickListener
            movieSelect(movie.movieId, binding.root)
        }
    }

    fun bind(movie: ActorMovie) {
        currentMovie = movie
        with(binding) {
            root.transitionName =
                context.getString(R.string.actor_movie_poster_transition_name, movie.movieId)

            moviePoster.setImg(movie.posterPicture)
            movieTitle.text = movie.title
        }
    }
}