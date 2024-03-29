package od.konstantin.myapplication.ui.actordetails

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import od.konstantin.myapplication.R
import od.konstantin.myapplication.data.models.ActorDetails
import od.konstantin.myapplication.databinding.FragmentActorDetailsBinding
import od.konstantin.myapplication.ui.FragmentNavigator
import od.konstantin.myapplication.ui.FragmentNavigator.Navigation.Back
import od.konstantin.myapplication.ui.FragmentNavigator.Navigation.ToMovieDetails
import od.konstantin.myapplication.ui.common.decorators.HorizontalListItemDecorator
import od.konstantin.myapplication.utils.extensions.*

class FragmentActorDetails : Fragment(R.layout.fragment_actor_details) {

    lateinit var viewModelFactory: ActorDetailsViewModelFactory

    private val viewModel: ActorDetailsViewModel by viewModels {
        viewModelFactory
    }

    private var moviesAdapter: ActorMoviesAdapter? = null

    private var fragmentNavigator: FragmentNavigator? = null

    private val binding by viewBindings { FragmentActorDetailsBinding.bind(it) }

    override fun onAttach(context: Context) {
        val actorDetailsComponent = DaggerActorDetailsComponent.factory()
            .create(appComponent)

        actorDetailsComponent.inject(this)

        super.onAttach(context)

        arguments?.getInt(KEY_ACTOR_ID)?.let { actorId ->
            viewModelFactory = actorDetailsComponent.viewModelFactoryProvider()
                .provideViewModelFactory(actorId)
        }

        if (context is FragmentNavigator) {
            fragmentNavigator = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAnimations(view)
        initListeners()
        initMoviesAdapter()
        initObservers()
    }

    private fun initListeners() {
        binding.buttonBack.setOnClickListener {
            fragmentNavigator?.navigate(Back)
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.updateActorData()
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.actorDetailsState.collect { state ->
                    state.actorDetails?.let(::displayActorDetails)
                    displayLoadingBar(state.isLoading)
                }
            }
        }
    }

    private fun initMoviesAdapter() {
        val moviesInnerMargin = resources.getDimension(R.dimen.actor_poster_inner_margin).toInt()
        val moviesDecorator = HorizontalListItemDecorator(moviesInnerMargin)
        moviesAdapter = ActorMoviesAdapter(::navigateToMovieDetails)
        binding.actorMovies.apply {
            addItemDecoration(moviesDecorator)
            adapter = moviesAdapter
        }
    }

    private fun displayActorDetails(actor: ActorDetails) {
        with(binding) {
            actorImage.setImg(actor.profilePicture)
            actorPoster.setImg(actor.profilePicture)
            actorPoster.setBlackAndWhiteEffect()
            actorName.text = actor.name
            actorPlaceOfBirth.text = actor.placeOfBirth
            actorKnownForDepartment.text = actor.knownForDepartment
            biography.text = actor.biography
            actorBirthday.setDateOrHide(actor.birthday, getString(R.string.actor_birth_date_format))
            moviesAdapter?.submitList(actor.movies)
            if (actor.biography.isEmpty()) {
                biographyLabel.hide()
            }
        }
    }

    private fun displayLoadingBar(isLoading: Boolean) {
        binding.swipeRefreshLayout.isRefreshing = isLoading
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
        view.doOnPreDraw { startPostponedEnterTransition() }

        val motionDuration = resources.getInteger(R.integer.motion_transition_duration).toLong()

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.root_container
            duration = motionDuration
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(getColor(R.color.background_color))
        }

        exitTransition = MaterialElevationScale(false).apply {
            duration = motionDuration
        }

        reenterTransition = MaterialElevationScale(true).apply {
            duration = motionDuration
        }
    }

    override fun onDestroyView() {
        moviesAdapter = null
        super.onDestroyView()
    }

    override fun onDetach() {
        fragmentNavigator = null
        super.onDetach()
    }

    companion object {
        private const val KEY_ACTOR_ID = "actorId"

        fun newInstance(actorId: Int): FragmentActorDetails {
            return FragmentActorDetails().apply {
                arguments = bundleOf(KEY_ACTOR_ID to actorId)
            }
        }
    }
}