package od.konstantin.myapplication.ui.actordetails

import dagger.Component
import dagger.assisted.AssistedFactory
import od.konstantin.myapplication.di.components.AppComponent
import od.konstantin.myapplication.di.scopes.FragmentScope

@FragmentScope
@Component(dependencies = [AppComponent::class])
interface ActorDetailsComponent {

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): ActorDetailsComponent
    }

    fun viewModelFactory(): ActorDetailsViewModelProvider

    fun viewModelFactoryProvider(): ActorDetailsViewModelFactoryProvider

    fun inject(fragment: FragmentActorDetails)

    @AssistedFactory
    interface ActorDetailsViewModelProvider {
        fun provideViewModel(actorId: Int): ActorDetailsViewModel
    }

    @AssistedFactory
    interface ActorDetailsViewModelFactoryProvider {
        fun provideViewModelFactory(actorId: Int): ActorDetailsViewModelFactory
    }
}