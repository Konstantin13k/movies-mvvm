package od.konstantin.myapplication.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import od.konstantin.myapplication.R
import od.konstantin.myapplication.ui.FragmentNavigator

class FragmentMain : Fragment() {

    private lateinit var bottomNavigation: BottomNavigationView

    private var fragmentNavigator: FragmentNavigator? = null

    override fun onAttach(context: Context) {
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
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNavigation = view.findViewById(R.id.movies_bottom_navigation)


        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            if (item.itemId != bottomNavigation.selectedItemId) {
                selectPage(item.itemId)
                true
            } else {
                false
            }
        }
    }

    override fun onStart() {
        super.onStart()
        selectPage(bottomNavigation.selectedItemId)
    }

    private fun selectPage(pageId: Int) {
        when (pageId) {
            R.id.movie_list_page -> fragmentNavigator?.navigate(FragmentNavigator.Navigation.ToMoviesList)
            R.id.favorite_movies_page -> fragmentNavigator?.navigate(FragmentNavigator.Navigation.ToFavoriteMovies)
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