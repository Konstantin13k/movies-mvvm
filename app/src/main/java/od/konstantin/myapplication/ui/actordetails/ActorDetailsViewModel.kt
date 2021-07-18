package od.konstantin.myapplication.ui.actordetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import od.konstantin.myapplication.data.ActorDetailsRepository
import od.konstantin.myapplication.data.models.ActorDetails

class ActorDetailsViewModel @AssistedInject constructor(
    private val actorDetailsRepository: ActorDetailsRepository,
    @Assisted private val actorId: Int,
) : ViewModel() {

    private val _actorDetailsState = MutableStateFlow(ActorDetailsState())
    val actorDetailsState: StateFlow<ActorDetailsState> = _actorDetailsState.asStateFlow()

    init {
        viewModelScope.launch {
            actorDetailsRepository.observeActorDetailsUpdates(actorId)
                .filterNotNull()
                .collect { actorDetails ->
                    _actorDetailsState.emit(
                        actorDetailsState.value.copy(
                            actorDetails = actorDetails,
                            isLoading = false
                        )
                    )
                }
        }
        updateActorData()
    }

    fun updateActorData() {
        viewModelScope.launch {
            _actorDetailsState.emit(actorDetailsState.value.copy(isLoading = true))
            actorDetailsRepository.updateActorData(actorId)
        }
    }

    data class ActorDetailsState(
        val actorDetails: ActorDetails? = null,
        val isLoading: Boolean = false
    )
}