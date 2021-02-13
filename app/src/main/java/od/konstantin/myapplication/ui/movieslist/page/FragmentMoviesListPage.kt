package od.konstantin.myapplication.ui.movieslist.page

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import kotlinx.coroutines.flow.collectLatest
import od.konstantin.myapplication.databinding.FragmentMovieListPageBinding
import od.konstantin.myapplication.ui.movieslist.MoviesSortType
import od.konstantin.myapplication.utils.extensions.appComponent
import javax.inject.Inject

class FragmentMoviesListPage : Fragment() {

    @Inject
    lateinit var viewModelFactory: MoviesListPageViewModelFactory

    private val viewModel: MoviesListPageViewModel by viewModels {
        viewModelFactory
    }

    private var movieSelectListener: MovieSelectListener? = null

    private var _binding: FragmentMovieListPageBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        DaggerMoviesListPageComponent.factory().create(
            appComponent,
            this,
        ).inject(this)

        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMovieListPageBinding.inflate(inflater, container, false)
        .also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initAdapter()

        val sortTypeId = arguments?.getInt(KEY_SORT_TYPE_ID)
        if (sortTypeId == null) {
            Toast.makeText(requireContext(), "Unknown movies sort type!!", Toast.LENGTH_LONG).show()
        } else {
            val sortType = MoviesSortType.getSortType(sortTypeId)
            viewModel.loadMovies(sortType)
        }
    }

    private fun initAdapter() {
        val adapter = MoviesListAdapter(viewLifecycleOwner, { movieAction ->
            when (movieAction) {
                is MoviesListAdapter.MovieAction.Select -> movieSelectListener?.onSelect(movieAction.movieId)
                is MoviesListAdapter.MovieAction.Like -> viewModel.likeMovie(
                    movieAction.movieId,
                    movieAction.isLiked
                )
            }
        })
        with(binding) {
            moviesList.adapter = adapter
            adapter.addLoadStateListener { loadState ->
                moviesList.isVisible = loadState.source.refresh is LoadState.NotLoading
                moviesLoadingBar.isVisible = loadState.source.refresh is LoadState.Loading
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.movies.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onDetach() {
        movieSelectListener = null
        super.onDetach()
    }

    fun setMovieSelectListener(listener: MovieSelectListener) {
        movieSelectListener = listener
    }

    fun interface MovieSelectListener {
        fun onSelect(movieId: Int)
    }

    companion object {
        private const val KEY_SORT_TYPE_ID = "sortTypeId"

        fun newInstance(sortTypeId: Int): FragmentMoviesListPage {
            return FragmentMoviesListPage().apply {
                arguments = bundleOf(KEY_SORT_TYPE_ID to sortTypeId)
            }
        }
    }
}