package od.konstantin.myapplication.ui.actordetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class ActorDetailsViewModelFactory @AssistedInject constructor(
    private val viewModelFactory: ActorDetailsComponent.ActorDetailsViewModelProvider,
    @Assisted private val actorId: Int,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        ActorDetailsViewModel::class.java -> viewModelFactory.provideViewModel(actorId)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}