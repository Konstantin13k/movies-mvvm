package od.konstantin.myapplication.ui.actordetails

import android.content.Context
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collectLatest
import od.konstantin.myapplication.R
import od.konstantin.myapplication.data.models.ActorDetails
import od.konstantin.myapplication.ui.moviedetails.FragmentMoviesDetails
import od.konstantin.myapplication.utils.extensions.appComponent
import od.konstantin.myapplication.utils.extensions.setImg
import java.text.SimpleDateFormat
import java.util.*

class FragmentActorDetails : Fragment() {

    lateinit var viewModelFactory: ActorDetailsViewModelFactory

    private val actorDetailsViewModel: ActorDetailsViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var backButton: Button

    private lateinit var actorName: TextView
    private lateinit var actorPicture: ImageView
    private lateinit var actorPoster: ImageView
    private lateinit var actorBirthday: TextView
    private lateinit var actorPlaceOfBirth: TextView
    private lateinit var actorKnownForDepartment: TextView
    private lateinit var actorBiography: TextView

    private lateinit var actorMovies: RecyclerView
    private lateinit var moviesAdapter: ActorMoviesAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val actorDetailsComponent = DaggerActorDetailsComponent.factory()
            .create(appComponent)

        actorDetailsComponent.inject(this)

        arguments?.getInt(KEY_ACTOR_ID)?.let { actorId ->
            viewModelFactory = actorDetailsComponent.viewModelFactoryProvider()
                .provideViewModelFactory(actorId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_actor_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewsFrom(view)
        addListenersToViews()
        addAdapterToRecyclerView()

        lifecycleScope.launchWhenStarted {
            actorDetailsViewModel.actorDetails.collectLatest { actor ->
                actor?.let { displayActorDetails(it) }
            }
        }
    }

    private fun initViewsFrom(view: View) {
        with(view) {
            backButton = findViewById(R.id.button_back)
            actorName = findViewById(R.id.tv_actor_name)
            actorPicture = findViewById(R.id.iv_actor_image)
            actorPoster = findViewById(R.id.iv_actor_poster)
            actorBirthday = findViewById(R.id.tv_actor_birthday)
            actorPlaceOfBirth = findViewById(R.id.tv_actor_place_of_birth)
            actorKnownForDepartment = findViewById(R.id.tv_actor_known_for_department)
            actorBiography = findViewById(R.id.tv_biography)
            actorMovies = findViewById(R.id.rv_actor_movies)
        }
    }

    private fun addListenersToViews() {
        backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun addAdapterToRecyclerView() {
        moviesAdapter = ActorMoviesAdapter { displayMovieDetails(it) }
        actorMovies.adapter = moviesAdapter
    }

    private fun displayActorDetails(actor: ActorDetails) {
        actorPicture.setImg(actor.profilePicture)
        actorPoster.setImg(actor.profilePicture)
        actorName.text = actor.name
//        actorBirthday.text = actor.birthday
        actorPlaceOfBirth.text = actor.placeOfBirth
        actorKnownForDepartment.text = actor.knownForDepartment
        actorBiography.text = actor.biography
        actorPoster.colorFilter = ColorMatrixColorFilter(ColorMatrix().apply { setSaturation(0f) })
        moviesAdapter.submitList(actor.movies)

        val birthdayDate = actor.birthday
        if (birthdayDate != null) {
            val dateFormat = getString(R.string.actor_birth_date_format)
            actorBirthday.text = SimpleDateFormat(dateFormat, Locale.getDefault())
                .format(birthdayDate)
        } else {
            actorBirthday.isVisible = false
        }
    }

    private fun displayMovieDetails(movieId: Int) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.root_container, FragmentMoviesDetails.newInstance(movieId))
            .addToBackStack(null)
            .commit()
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