package od.konstantin.myapplication.moviedetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import od.konstantin.myapplication.R

class FragmentMoviesDetails : Fragment() {

    private var backToMovieListListener: BackToMovieListListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val backButton = view.findViewById<Button>(R.id.button_back)
        backButton.setOnClickListener {
            backToMovieListListener?.backToMovieList()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BackToMovieListListener) {
            backToMovieListListener = context
        }
    }

    override fun onDetach() {
        backToMovieListListener = null
        super.onDetach()
    }

    interface BackToMovieListListener {
        fun backToMovieList()
    }
}