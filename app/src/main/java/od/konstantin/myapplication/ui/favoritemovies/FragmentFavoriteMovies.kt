package od.konstantin.myapplication.ui.favoritemovies

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
import od.konstantin.myapplication.R
import od.konstantin.myapplication.ui.movieslist.FragmentMoviesList
import od.konstantin.myapplication.utils.extensions.appComponent
import javax.inject.Inject

class FragmentFavoriteMovies : Fragment() {

    @Inject
    lateinit var favoriteMoviesViewModelFactory: FavoriteMoviesViewModelFactory

    private val viewModel: FavoriteMoviesViewModel by viewModels {
        favoriteMoviesViewModelFactory
    }

    private lateinit var favoriteMoviesRecyclerView: RecyclerView
    private lateinit var favoriteMoviesAdapter: FavoriteMoviesAdapter

    private var showMovieDetailsListener: FragmentMoviesList.ShowMovieDetailsListener? = null

    override fun onAttach(context: Context) {
        DaggerFavoriteMoviesComponent.factory().create(appComponent)
            .inject(this)

        super.onAttach(context)

        if (context is FragmentMoviesList.ShowMovieDetailsListener) {
            showMovieDetailsListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteMoviesRecyclerView = view.findViewById(R.id.rv_favorite_movies_list)

        initAdapter()
    }

    private fun initAdapter() {
        favoriteMoviesAdapter = FavoriteMoviesAdapter { favoriteMovie ->
            showMovieDetailsListener?.showMovieDetails(favoriteMovie.movieId)
        }
        favoriteMoviesRecyclerView.adapter = favoriteMoviesAdapter

        lifecycleScope.launchWhenCreated {
            viewModel.favoriteMovies.collectLatest {
                favoriteMoviesAdapter.submitList(it)
            }
        }
    }

    override fun onDetach() {
        showMovieDetailsListener = null
        super.onDetach()
    }

    companion object {
        fun newInstance(): FragmentFavoriteMovies {
            return FragmentFavoriteMovies()
        }
    }
}