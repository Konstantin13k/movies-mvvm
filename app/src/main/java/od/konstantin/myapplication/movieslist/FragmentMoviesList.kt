package od.konstantin.myapplication.movieslist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import od.konstantin.myapplication.R
import od.konstantin.myapplication.data.MoviesRepository
import od.konstantin.myapplication.data.remote.MoviesApi
import od.konstantin.myapplication.domain.MoviesDataSource
import od.konstantin.myapplication.movieslist.adapter.MoviesListAdapter

class FragmentMoviesList : Fragment() {

    // Использовал application context, т.к. он будет жить пока будет жить приложение,
    // а context активности или фрагмента будет уничтожен после уничтожения активности/фрагмента
    private val moviesListViewModel: MoviesListViewModel by viewModels {
        MoviesListViewModelFactory(
            MoviesDataSource(requireContext().applicationContext),
            MoviesRepository(MoviesApi.moviesApi)
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
        adapter = MoviesListAdapter { movieId ->
            moviesListViewModel.selectMovie(movieId)
        }
        recyclerView.adapter = adapter
        moviesListViewModel.selectedMovie.observe(viewLifecycleOwner, { movieId ->
            if (movieId != null) {
                showMovieDetailsListener?.showMovieDetails(movieId)
                moviesListViewModel.showMovieDetailsDone()
            }
        })
        lifecycleScope.launch {
            moviesListViewModel.loadPagingMovies().collectLatest {
                adapter.submitData(it)
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
        fun showMovieDetails(movieId: Int)
    }
}