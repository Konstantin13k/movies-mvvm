package od.konstantin.myapplication.ui.movieslist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import od.konstantin.myapplication.R
import od.konstantin.myapplication.ui.FragmentNavigator
import od.konstantin.myapplication.ui.movieslist.page.FragmentMoviesListPage
import od.konstantin.myapplication.ui.movieslist.page.MoviesListPageAdapter
import od.konstantin.myapplication.utils.extensions.appComponent
import od.konstantin.myapplication.utils.extensions.observeEvents
import javax.inject.Inject

class FragmentMoviesList : Fragment() {

    @Inject
    lateinit var viewModelFactory: MoviesListViewModelFactory

    private val moviesListViewModel: MoviesListViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var moviesSortSelector: TabLayout
    private lateinit var moviesViewPager: ViewPager2

    private var fragmentNavigator: FragmentNavigator? = null

    override fun onAttach(context: Context) {
        DaggerMoviesListComponent.factory()
            .create(appComponent)
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
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        moviesSortSelector = view.findViewById(R.id.tl_movies_sort_type)
        moviesViewPager = view.findViewById(R.id.vp_movie_list_pager)

        initObservers()
        initMoviesViewPager()
    }

    private fun initObservers() {
        moviesListViewModel.selectedMovie.observeEvents(viewLifecycleOwner) { movieId ->
            fragmentNavigator?.navigate(FragmentNavigator.Navigation.ToMovieDetails(movieId), true)
        }
    }

    private fun initMoviesViewPager() {
        val moviesListPageAdapter = MoviesListPageAdapter(this)

        moviesViewPager.adapter = moviesListPageAdapter
        TabLayoutMediator(moviesSortSelector, moviesViewPager) { tab, position ->
            tab.text = getTabName(position)
        }.attach()
    }

    private fun getTabName(id: Int): String {
        val tabName = when (id) {
            0 -> R.string.tab_item_now_playing
            1 -> R.string.tab_item_popular
            2 -> R.string.tab_item_top_rated
            3 -> R.string.tab_item_upcoming
            else -> throw IllegalArgumentException("Not found sort type with id: $id")
        }
        return getString(tabName)
    }

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        if (childFragment is FragmentMoviesListPage) {
            childFragment.setMovieSelectListener { movieId ->
                moviesListViewModel.selectMovie(movieId)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (::moviesSortSelector.isInitialized) {
            val currentTabPosition = moviesSortSelector.selectedTabPosition
            outState.putInt(KEY_SELECTED_TAB_POSITION, currentTabPosition)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.getInt(KEY_SELECTED_TAB_POSITION)?.let { position ->
            moviesSortSelector.getTabAt(position)?.select()
        }
    }

    override fun onDetach() {
        fragmentNavigator = null
        super.onDetach()
    }

    companion object {
        private const val KEY_SELECTED_TAB_POSITION = "selectedTabPosition"

        fun newInstance(): FragmentMoviesList {
            return FragmentMoviesList()
        }
    }
}