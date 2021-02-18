package od.konstantin.myapplication.ui.actordetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import od.konstantin.myapplication.data.ActorDetailsRepository
import od.konstantin.myapplication.data.models.ActorDetails

class ActorDetailsViewModel @AssistedInject constructor(
    private val actorDetailsRepository: ActorDetailsRepository,
    @Assisted private val actorId: Int,
) : ViewModel() {

    val actorDetails: Flow<ActorDetails?>
        get() = actorDetailsRepository.observeActorDetailsUpdates(actorId)

    init {
        viewModelScope.launch {
            actorDetailsRepository.updateActorData(actorId)
        }
    }
}