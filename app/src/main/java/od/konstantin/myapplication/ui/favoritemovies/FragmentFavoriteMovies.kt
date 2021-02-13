package od.konstantin.myapplication.ui.favoritemovies

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collectLatest
import od.konstantin.myapplication.R
import od.konstantin.myapplication.ui.FragmentNavigator
import od.konstantin.myapplication.utils.extensions.appComponent
import javax.inject.Inject

class FragmentFavoriteMovies : Fragment() {

    @Inject
    lateinit var favoriteMoviesViewModelFactory: FavoriteMoviesViewModelFactory

    private val viewModel: FavoriteMoviesViewModel by viewModels {
        favoriteMoviesViewModelFactory
    }

    private lateinit var backButton: Button
    private lateinit var favoriteMoviesRecyclerView: RecyclerView
    private lateinit var favoriteMoviesAdapter: FavoriteMoviesAdapter

    private var fragmentNavigator: FragmentNavigator? = null

    override fun onAttach(context: Context) {
        DaggerFavoriteMoviesComponent.factory().create(appComponent)
            .inject(this)

        super.onAttach(context)

        if (context is FragmentNavigator) {
            fragmentNavigator = context
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
        backButton = view.findViewById(R.id.button_back)
        favoriteMoviesRecyclerView = view.findViewById(R.id.rv_favorite_movies_list)

        initAdapter()
        initListeners()
    }

    private fun initAdapter() {
        favoriteMoviesAdapter = FavoriteMoviesAdapter { movieAction ->
            when (movieAction) {
                is FavoriteMoviesAdapter.MovieAction.Select -> fragmentNavigator?.navigate(
                    FragmentNavigator.Navigation.ToMovieDetails(movieAction.movieId),
                    addToBackStack = true
                )
                is FavoriteMoviesAdapter.MovieAction.Unlike -> viewModel.unlikeMovie(movieAction.movieId)
            }
        }
        favoriteMoviesRecyclerView.adapter = favoriteMoviesAdapter

        lifecycleScope.launchWhenCreated {
            viewModel.favoriteMovies.collectLatest {
                favoriteMoviesAdapter.submitList(it)
            }
        }
    }

    private fun initListeners() {
        backButton.setOnClickListener {
            fragmentNavigator?.navigate(FragmentNavigator.Navigation.Back)
        }
    }

    override fun onDetach() {
        fragmentNavigator = null
        super.onDetach()
    }

    companion object {
        fun newInstance(): FragmentFavoriteMovies {
            return FragmentFavoriteMovies()
        }
    }
}