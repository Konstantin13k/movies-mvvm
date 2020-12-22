package od.konstantin.myapplication.movieslist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import od.konstantin.myapplication.R
import od.konstantin.myapplication.domain.MoviesDataSource

class FragmentMoviesList : Fragment() {

    // Использовал application context, т.к. он будет жить пока будет жить приложение,
    // а context активности или фрагмента будет уничтожен после уничтожения активности/фрагмента
    private val moviesListViewModel: MoviesListViewModel by viewModels {
        MoviesListViewModelFactory(
            MoviesDataSource(requireContext().applicationContext)
        )
    }

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
            moviesListViewModel.selectMovie(movie)
        }
        recyclerView.adapter = adapter

        moviesListViewModel.movies.observe(viewLifecycleOwner, { movies ->
            adapter.submitList(movies)
        })
        moviesListViewModel.selectedMovie.observe(viewLifecycleOwner, { movie ->
            if (movie != null) {
                showMovieDetailsListener?.showMovieDetails(movie.id)
                moviesListViewModel.showMovieDetailsDone()
            }
        })
        moviesListViewModel.loadMovies()
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
        fun showMovieDetails(movieId: Int)
    }
}