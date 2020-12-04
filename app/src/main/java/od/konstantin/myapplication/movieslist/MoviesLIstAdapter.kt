package od.konstantin.myapplication.movieslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.willy.ratingbar.ScaleRatingBar
import od.konstantin.myapplication.R
import od.konstantin.myapplication.data.models.Movie

class MoviesListAdapter(private val listener: OnClickListener) :
    ListAdapter<Movie, MoviesListAdapter.MovieHolder>(MovieCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        return MovieHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.view_holder_movie, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
        holder.itemView.setOnClickListener {
            listener.onClick(movie)
        }
    }

    class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val moviePoster: ImageView = itemView.findViewById(R.id.iv_movie_poster)
        private val movieTitle: TextView = itemView.findViewById(R.id.tv_movie_poster_title)
        private val movieTags: TextView = itemView.findViewById(R.id.tv_movie_tags)
        private val moviePg: TextView = itemView.findViewById(R.id.tv_movie_pg)
        private val movieRating: ScaleRatingBar = itemView.findViewById(R.id.rb_movie_rating)
        private val movieReviews: TextView = itemView.findViewById(R.id.tv_movie_reviews)
        private val movieLength: TextView = itemView.findViewById(R.id.tv_movie_length)

        fun bind(movie: Movie) {
            Glide.with(context).load(movie.smallPosterId).into(moviePoster)
            movieTitle.text = movie.movieTitle
            movieTags.text = movie.tags.joinToString(", ")
            moviePg.text = context.getString(R.string.movie_pg, movie.pg)
            movieRating.rating = movie.rating
            movieReviews.text = context.getString(R.string.movie_reviews, movie.reviews)
            movieLength.text = context.getString(R.string.movie_length, movie.movieLength)
        }
    }

    fun interface OnClickListener {
        fun onClick(movie: Movie)
    }
}

private val RecyclerView.ViewHolder.context
    get() = itemView.context

class MovieCallback : DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.movieTitle == newItem.movieTitle
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}