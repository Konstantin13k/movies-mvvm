package od.konstantin.myapplication.movieslist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import od.konstantin.myapplication.R

class FragmentMoviesList : Fragment() {

    private var showMovieDetailsListener: ShowMovieDetailsListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val movieContainer = view.findViewById<View>(R.id.movie_container)
        movieContainer.setOnClickListener {
            showMovieDetailsListener?.showMovieDetails()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ShowMovieDetailsListener) {
            showMovieDetailsListener = context
        }
    }

    override fun onDetach() {
        showMovieDetailsListener = null
        super.onDetach()
    }

    interface ShowMovieDetailsListener {
        fun showMovieDetails()
    }
}