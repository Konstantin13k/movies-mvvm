package od.konstantin.myapplication.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import od.konstantin.myapplication.MyApplication
import od.konstantin.myapplication.R
import od.konstantin.myapplication.databinding.ActivityMainBinding
import od.konstantin.myapplication.ui.FragmentNavigator.Navigation
import od.konstantin.myapplication.ui.actordetails.FragmentActorDetails
import od.konstantin.myapplication.ui.favoritemovies.FragmentFavoriteMovies
import od.konstantin.myapplication.ui.moviedetails.FragmentMoviesDetails
import od.konstantin.myapplication.ui.movieslist.FragmentMoviesList
import od.konstantin.myapplication.utils.extensions.hide
import od.konstantin.myapplication.utils.extensions.hideBottomNavigation
import od.konstantin.myapplication.utils.extensions.showBottomNavigation

class MainActivity : AppCompatActivity(), FragmentNavigator {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListeners()

        if (savedInstanceState == null) {
            val intentHandled = intent?.let(::handleIntent) ?: false
            if (!intentHandled) {
                selectPage(binding.moviesBottomNavigation.selectedItemId)
            }
        }
    }

    private fun handleIntent(intent: Intent): Boolean {
        when (intent.action) {
            Intent.ACTION_VIEW -> {
                intent.data?.lastPathSegment?.toIntOrNull()?.let { movieId ->
                    (application as MyApplication).movieNotifications.dismissNotification(movieId)
                    navigate(Navigation.ToMovieDetails(movieId), addToBackStack = false)
                    binding.moviesBottomNavigation.hide()
                    return true
                }
            }
        }
        return false
    }

    private fun initListeners() {
        with(binding.moviesBottomNavigation) {
            setOnNavigationItemSelectedListener { item ->
                if (item.itemId != selectedItemId) {
                    selectPage(item.itemId)
                    true
                } else {
                    false
                }
            }
        }
        supportFragmentManager.addOnBackStackChangedListener {
            val fragment = supportFragmentManager.findFragmentById(R.id.root_container)
            if (fragment is FragmentMoviesList || fragment is FragmentFavoriteMovies) {
                binding.moviesBottomNavigation.showBottomNavigation()
            } else {
                binding.moviesBottomNavigation.hideBottomNavigation()
            }
        }
    }

    private fun selectPage(pageId: Int) {
        when (pageId) {
            R.id.movie_list_page -> navigate(Navigation.ToMoviesList)
            R.id.favorite_movies_page -> navigate(Navigation.ToFavoriteMovies)
        }
    }

    override fun navigate(navigation: Navigation, addToBackStack: Boolean) {
        val navigationInfo: NavigationInfo = when (navigation) {
            Navigation.Back -> {
                supportFragmentManager.popBackStack()
                return
            }
            Navigation.ToMoviesList -> {
                NavigationInfo(
                    fragment = FragmentMoviesList.newInstance(),
                    rootContainerId = R.id.root_container,
                )
            }
            Navigation.ToFavoriteMovies -> {
                NavigationInfo(
                    fragment = FragmentFavoriteMovies.newInstance(),
                    rootContainerId = R.id.root_container,
                )
            }
            is Navigation.ToMovieDetails -> {
                val movieTransitionName = getString(R.string.movie_poster_details_transition_name)
                NavigationInfo(
                    fragment = FragmentMoviesDetails.newInstance(navigation.movieId),
                    rootContainerId = R.id.root_container,
                    addToBackStack = addToBackStack,
                    sharedView = navigation.movieCardView,
                    sharedViewTransitionName = movieTransitionName
                )
            }
            is Navigation.ToActorDetails -> {
                val actorTransitionName = getString(R.string.actor_poster_details_transition_name)
                NavigationInfo(
                    fragment = FragmentActorDetails.newInstance(navigation.actorId),
                    rootContainerId = R.id.root_container,
                    addToBackStack = addToBackStack,
                    sharedView = navigation.actorCardView,
                    sharedViewTransitionName = actorTransitionName
                )
            }
        }
        navigate(navigationInfo)
    }

    private fun navigate(navigationInfo: NavigationInfo) {
        val fragment = navigationInfo.fragment
        val rootContainerId = navigationInfo.rootContainerId
        val addToBackStack = navigationInfo.addToBackStack
        val sharedViewTransitionName = navigationInfo.sharedViewTransitionName

        supportFragmentManager.beginTransaction().apply {
            replace(rootContainerId, fragment)
            if (addToBackStack) {
                addToBackStack(null)
            }
            navigationInfo.sharedView?.let { sharedView ->
                addSharedElement(sharedView, sharedViewTransitionName)
            }
        }.commit()
    }

    private class NavigationInfo(
        val fragment: Fragment,
        val rootContainerId: Int,
        val addToBackStack: Boolean = false,
        val sharedView: View? = null,
        val sharedViewTransitionName: String = ""
    )

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}