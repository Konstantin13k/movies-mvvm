package od.konstantin.myapplication.ui.main

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import od.konstantin.myapplication.R
import od.konstantin.myapplication.databinding.FragmentMainBinding
import od.konstantin.myapplication.ui.FragmentNavigator
import od.konstantin.myapplication.ui.FragmentNavigator.Navigation.ToFavoriteMovies
import od.konstantin.myapplication.ui.FragmentNavigator.Navigation.ToMoviesList
import od.konstantin.myapplication.ui.favoritemovies.FragmentFavoriteMovies
import od.konstantin.myapplication.ui.movieslist.FragmentMoviesList
import od.konstantin.myapplication.utils.extensions.hideBottomNavigation
import od.konstantin.myapplication.utils.extensions.showBottomNavigation
import od.konstantin.myapplication.utils.extensions.viewBindings

class FragmentMain : Fragment(R.layout.fragment_main) {

    private var fragmentNavigator: FragmentNavigator? = null

    private val binding by viewBindings { FragmentMainBinding.bind(it) }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is FragmentNavigator) {
            fragmentNavigator = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.moviesBottomNavigation.apply {
            setOnNavigationItemSelectedListener { item ->
                if (item.itemId != selectedItemId) {
                    selectPage(item.itemId)
                    true
                } else {
                    false
                }
            }
        }

        val fragmentManager = requireActivity().supportFragmentManager
        fragmentManager.addOnBackStackChangedListener {
            val fragment = fragmentManager.findFragmentById(R.id.main_fragment)
            if (fragment is FragmentMoviesList || fragment is FragmentFavoriteMovies) {
                binding.moviesBottomNavigation.showBottomNavigation()
            } else {
                binding.moviesBottomNavigation.hideBottomNavigation()
            }
        }

        if (savedInstanceState == null) {
            selectPage(binding.moviesBottomNavigation.selectedItemId)
        }
    }

    private fun selectPage(pageId: Int) {
        when (pageId) {
            R.id.movie_list_page -> fragmentNavigator?.navigate(ToMoviesList)
            R.id.favorite_movies_page -> fragmentNavigator?.navigate(ToFavoriteMovies)
        }
    }

    override fun onDetach() {
        fragmentNavigator = null
        super.onDetach()
    }

    companion object {
        fun newInstance(): FragmentMain {
            return FragmentMain()
        }
    }
}