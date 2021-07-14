package od.konstantin.myapplication.ui.moviedetails.adapter

import androidx.recyclerview.widget.RecyclerView
import od.konstantin.myapplication.data.models.Actor
import od.konstantin.myapplication.databinding.ViewHolderActorBinding
import od.konstantin.myapplication.utils.extensions.setImg

class ActorViewHolder(private val binding: ViewHolderActorBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(actor: Actor) {
        with(binding) {
            root.transitionName = "actor_${actor.id}"
            actorImage.setImg(actor.picture)
            actorName.text = actor.name
        }
    }
}