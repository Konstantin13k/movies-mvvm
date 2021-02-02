package od.konstantin.myapplication.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import od.konstantin.myapplication.R
import od.konstantin.myapplication.ui.favoritemovies.FragmentFavoriteMovies
import od.konstantin.myapplication.ui.movieslist.FragmentMoviesList

class FragmentMain : Fragment() {

    private lateinit var bottomNavigation: BottomNavigationView

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
        val selectedFragment = when (pageId) {
            R.id.movie_list_page -> FragmentMoviesList.newInstance()
            R.id.favorite_movies_page -> FragmentFavoriteMovies.newInstance()
            else -> null
        }
        if (selectedFragment != null) {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment, selectedFragment)
                .commit()
        }
    }

    companion object {
        fun newInstance(): FragmentMain {
            return FragmentMain()
        }
    }
}