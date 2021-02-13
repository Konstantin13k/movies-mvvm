package od.konstantin.myapplication.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import od.konstantin.myapplication.R
import od.konstantin.myapplication.databinding.FragmentMainBinding
import od.konstantin.myapplication.ui.FragmentNavigator

class FragmentMain : Fragment() {

    private var fragmentNavigator: FragmentNavigator? = null

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

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
    ): View = FragmentMainBinding.inflate(inflater, container, false)
        .also { _binding = it }.root

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
    }

    override fun onStart() {
        super.onStart()
        selectPage(binding.moviesBottomNavigation.selectedItemId)
    }

    private fun selectPage(pageId: Int) {
        when (pageId) {
            R.id.movie_list_page -> fragmentNavigator?.navigate(FragmentNavigator.Navigation.ToMoviesList)
            R.id.favorite_movies_page -> fragmentNavigator?.navigate(FragmentNavigator.Navigation.ToFavoriteMovies)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
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