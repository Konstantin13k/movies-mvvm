package od.konstantin.myapplication.ui.actordetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import od.konstantin.myapplication.data.ActorDetailsRepository
import od.konstantin.myapplication.data.models.ActorDetails

class ActorDetailsViewModel @AssistedInject constructor(
    private val actorDetailsRepository: ActorDetailsRepository,
    @Assisted private val actorId: Int,
) : ViewModel() {

    private val _actorDetails: MutableStateFlow<ActorDetails?> = MutableStateFlow(null)
    val actorDetails: StateFlow<ActorDetails?> = _actorDetails.asStateFlow()

    init {
        viewModelScope.launch {
            actorDetailsRepository.observeActorDetailsUpdates(actorId)
                .collect(_actorDetails::emit)
        }
        viewModelScope.launch {
            actorDetailsRepository.updateActorData(actorId)
        }
    }
}