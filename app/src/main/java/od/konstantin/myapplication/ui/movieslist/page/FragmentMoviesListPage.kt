package od.konstantin.myapplication.ui.movieslist.page

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import kotlinx.coroutines.flow.collect
import od.konstantin.myapplication.R
import od.konstantin.myapplication.databinding.FragmentMovieListPageBinding
import od.konstantin.myapplication.ui.movieslist.MoviesSortType
import od.konstantin.myapplication.utils.extensions.appComponent
import od.konstantin.myapplication.utils.extensions.hide
import od.konstantin.myapplication.utils.extensions.show
import od.konstantin.myapplication.utils.extensions.viewBindings
import javax.inject.Inject

class FragmentMoviesListPage : Fragment(R.layout.fragment_movie_list_page) {

    @Inject
    lateinit var viewModelFactory: MoviesListPageViewModelFactory

    private val viewModel: MoviesListPageViewModel by viewModels {
        viewModelFactory
    }

    private var movieSelectListener: MovieSelectListener? = null

    private val binding by viewBindings { FragmentMovieListPageBinding.bind(it) }

    override fun onAttach(context: Context) {
        DaggerMoviesListPageComponent.factory().create(
            appComponent,
            this,
        ).inject(this)

        super.onAttach(context)
    }

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
                is MoviesListAdapter.MovieAction.Select -> movieSelectListener?.onSelect(
                    movieAction.movie.id,
                    movieAction.cardView
                )
                is MoviesListAdapter.MovieAction.Like -> viewModel.likeMovie(
                    movieAction.movieId,
                    movieAction.isLiked
                )
            }
        })
        val outerItemsMargin = resources.getDimension(R.dimen.small_poster_margin).toInt()
        val innerItemsMargin = outerItemsMargin / 2
        val moviesListItemDecoration = MoviesListItemDecoration(innerItemsMargin, outerItemsMargin)

        with(binding) {
            moviesList.setHasFixedSize(true)
            moviesList.addItemDecoration(moviesListItemDecoration)
            moviesList.adapter = adapter
            adapter.addLoadStateListener { loadState ->
                moviesList.isVisible = loadState.source.refresh is LoadState.NotLoading
                val isLoading = loadState.source.refresh is LoadState.Loading
                if (isLoading) {
                    moviesLoadingBar.show()
                    moviesLoadingBar.playAnimation()
                } else {
                    moviesLoadingBar.pauseAnimation()
                    moviesLoadingBar.hide()
                }
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.movies.collect(adapter::submitData)
        }
    }

    override fun onDetach() {
        movieSelectListener = null
        super.onDetach()
    }

    fun setMovieSelectListener(listener: MovieSelectListener) {
        movieSelectListener = listener
    }

    fun interface MovieSelectListener {
        fun onSelect(movieId: Int, movieCardView: View)
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