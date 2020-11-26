package od.konstantin.myapplication.movieslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import od.konstantin.myapplication.R

class FragmentMoviesList : Fragment() {

    private var clickListener: ClickListener? = null

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
            clickListener?.showMovieDetails()
        }
    }

    fun interface ClickListener {
        fun showMovieDetails()
    }

    override fun onDetach() {
        clickListener = null
        super.onDetach()
    }

    companion object {
        fun newInstance(clickListener: ClickListener): FragmentMoviesList {
            val fragment = FragmentMoviesList()
            fragment.clickListener = clickListener
            return fragment
        }
    }
}