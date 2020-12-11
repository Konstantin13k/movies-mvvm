package od.konstantin.myapplication.movieslist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import od.konstantin.myapplication.R
import od.konstantin.myapplication.data.models.Movie
import od.konstantin.myapplication.domain.MoviesDataSource

class FragmentMoviesList : Fragment() {

    private val fragmentScope = CoroutineScope(Dispatchers.Default + Job())

    private var showMovieDetailsListener: ShowMovieDetailsListener? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MoviesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.rv_movies_list)
        adapter = MoviesListAdapter { movie ->
            showMovieDetailsListener?.showMovieDetails(movie)
        }
        recyclerView.adapter = adapter

        showMovies()
    }

    private fun showMovies() {
        fragmentScope.launch {
            val dataSource = MoviesDataSource()
            val movies = dataSource.getMovies(requireContext())
            withContext(Dispatchers.Main) {
                adapter.submitList(movies)
            }
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
        fun showMovieDetails(movie: Movie)
    }
}