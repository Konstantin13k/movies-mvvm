package od.konstantin.myapplication.ui.movieslist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import od.konstantin.myapplication.R
import od.konstantin.myapplication.data.MoviesRepository
import od.konstantin.myapplication.ui.movieslist.adapter.MoviesListAdapter

class FragmentMoviesList : Fragment() {

    private val moviesListViewModel: MoviesListViewModel by viewModels {
        MoviesListViewModelFactory(MoviesRepository.getRepository())
    }

    private lateinit var moviesSortSelector: TabLayout
    private lateinit var moviesLoadingBar: ProgressBar

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
        moviesSortSelector = view.findViewById(R.id.tl_movies_sort_type)
        moviesLoadingBar = view.findViewById(R.id.pb_movies_loading_bar)
        recyclerView = view.findViewById(R.id.rv_movies_list)

        initAdapter()
        initTabSelector()
        moviesListViewModel.selectedMovie.observe(viewLifecycleOwner, { movieId ->
            if (movieId != null) {
                showMovieDetailsListener?.showMovieDetails(movieId)
                moviesListViewModel.showMovieDetailsDone()
            }
        })
        moviesListViewModel.sortType.observe(viewLifecycleOwner, { sortType ->
            lifecycleScope.launch {
                moviesListViewModel.loadMovies(sortType).collectLatest {
                    adapter.submitData(it)
                }
            }
        })
    }

    private fun initAdapter() {
        adapter = MoviesListAdapter { movieId ->
            moviesListViewModel.selectMovie(movieId)
        }
        recyclerView.adapter = adapter
        adapter.addLoadStateListener { loadState ->
            recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
            moviesLoadingBar.isVisible = loadState.source.refresh is LoadState.Loading
        }
    }

    private fun initTabSelector() {
        moviesListViewModel.sortType.value?.let { sortType ->
            moviesSortSelector.getTabAt(sortType.id)?.select()
        }
        moviesSortSelector.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                moviesListViewModel.changeSortType(tab.position)
                recyclerView.scrollToPosition(0)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
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