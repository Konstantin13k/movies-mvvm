package od.konstantin.myapplication.ui.movieslist

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.transition.MaterialElevationScale
import od.konstantin.myapplication.R
import od.konstantin.myapplication.databinding.FragmentMoviesListBinding
import od.konstantin.myapplication.ui.FragmentNavigator
import od.konstantin.myapplication.ui.FragmentNavigator.Navigation.ToMovieDetails
import od.konstantin.myapplication.ui.movieslist.page.FragmentMoviesListPage
import od.konstantin.myapplication.ui.movieslist.page.MoviesListPageAdapter
import od.konstantin.myapplication.utils.extensions.appComponent
import od.konstantin.myapplication.utils.extensions.viewBindings

class FragmentMoviesList : Fragment(R.layout.fragment_movies_list) {

    private var fragmentNavigator: FragmentNavigator? = null

    private val binding by viewBindings { FragmentMoviesListBinding.bind(it) }

    override fun onAttach(context: Context) {
        DaggerMoviesListComponent.factory()
            .create(appComponent)
            .inject(this)

        super.onAttach(context)

        if (context is FragmentNavigator) {
            fragmentNavigator = context
        }
    }

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        if (childFragment is FragmentMoviesListPage) {
            childFragment.setMovieSelectListener(::navigateToMovieDetails)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAnimations(view)
        initMoviesViewPager()
    }

    private fun initMoviesViewPager() {
        val moviesListPageAdapter = MoviesListPageAdapter(this)

        binding.movieListPager.adapter = moviesListPageAdapter
        TabLayoutMediator(binding.moviesSortType, binding.movieListPager) { tab, position ->
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

    private fun navigateToMovieDetails(movieId: Int, movieCardView: View) {
        fragmentNavigator?.navigate(
            ToMovieDetails(
                movieId,
                movieCardView
            )
        )
    }

    private fun initAnimations(view: View) {
        postponeEnterTransition()
        view.doOnPreDraw {
            startPostponedEnterTransition()
        }

        val motionDuration = resources.getInteger(R.integer.motion_transition_duration).toLong()

        enterTransition = MaterialElevationScale(true).apply {
            duration = motionDuration
        }

        exitTransition = MaterialElevationScale(false).apply {
            duration = motionDuration
        }

        reenterTransition = MaterialElevationScale(true).apply {
            duration = motionDuration
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (view != null) {
            val currentTabPosition = binding.moviesSortType.selectedTabPosition
            outState.putInt(KEY_SELECTED_TAB_POSITION, currentTabPosition)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.getInt(KEY_SELECTED_TAB_POSITION)?.let { position ->
            binding.moviesSortType.getTabAt(position)?.select()
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