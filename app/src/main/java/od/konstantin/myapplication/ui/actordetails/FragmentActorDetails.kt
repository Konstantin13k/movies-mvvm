package od.konstantin.myapplication.ui.actordetails

import android.content.Context
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import od.konstantin.myapplication.R
import od.konstantin.myapplication.data.models.ActorDetails
import od.konstantin.myapplication.databinding.FragmentActorDetailsBinding
import od.konstantin.myapplication.ui.FragmentNavigator
import od.konstantin.myapplication.utils.extensions.appComponent
import od.konstantin.myapplication.utils.extensions.hide
import od.konstantin.myapplication.utils.extensions.setDate
import od.konstantin.myapplication.utils.extensions.setImg

class FragmentActorDetails : Fragment() {

    lateinit var viewModelFactory: ActorDetailsViewModelFactory

    private val actorDetailsViewModel: ActorDetailsViewModel by viewModels {
        viewModelFactory
    }

    private var moviesAdapter: ActorMoviesAdapter? = null

    private var fragmentNavigator: FragmentNavigator? = null

    private var _binding: FragmentActorDetailsBinding? = null
    private val binding get() = _binding!!

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentActorDetailsBinding.inflate(inflater, container, false)
        .also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addListenersToViews()
        addAdapterToRecyclerView()

        lifecycleScope.launchWhenStarted {
            actorDetailsViewModel.actorDetails.collectLatest { actor ->
                actor?.let { displayActorDetails(it) }
            }
        }
    }

    private fun addListenersToViews() {
        binding.buttonBack.setOnClickListener {
            fragmentNavigator?.navigate(FragmentNavigator.Navigation.Back)
        }
    }

    private fun addAdapterToRecyclerView() {
        moviesAdapter = ActorMoviesAdapter { displayMovieDetails(it) }
        binding.actorMovies.adapter = moviesAdapter
    }

    private fun displayActorDetails(actor: ActorDetails) {
        with(binding) {
            actorImage.setImg(actor.profilePicture)
            actorPoster.setImg(actor.profilePicture)
            actorName.text = actor.name
            actorPlaceOfBirth.text = actor.placeOfBirth
            actorKnownForDepartment.text = actor.knownForDepartment
            biography.text = actor.biography
            actorPoster.colorFilter =
                ColorMatrixColorFilter(ColorMatrix().apply { setSaturation(0f) })
            moviesAdapter?.submitList(actor.movies)

            val birthdayDate = actor.birthday
            if (birthdayDate != null) {
                actorBirthday.setDate(birthdayDate, getString(R.string.actor_birth_date_format))
            } else {
                actorBirthday.hide()
            }
        }
    }

    private fun displayMovieDetails(movieId: Int) {
        fragmentNavigator?.navigate(FragmentNavigator.Navigation.ToMovieDetails(movieId), true)
    }

    override fun onDestroyView() {
        moviesAdapter = null
        _binding = null
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