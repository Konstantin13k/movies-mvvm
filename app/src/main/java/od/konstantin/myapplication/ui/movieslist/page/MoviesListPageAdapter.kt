package od.konstantin.myapplication.ui.movieslist.page

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import od.konstantin.myapplication.ui.movieslist.MoviesSortType

class MoviesListPageAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = MoviesSortType.values().size

    override fun createFragment(position: Int): Fragment {
        return FragmentMoviesListPage.newInstance(position)
    }
}