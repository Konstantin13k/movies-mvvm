package od.konstantin.myapplication.ui.moviedetails.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import od.konstantin.myapplication.R
import od.konstantin.myapplication.data.models.Actor
import od.konstantin.myapplication.databinding.ViewHolderActorBinding
import od.konstantin.myapplication.utils.extensions.context
import od.konstantin.myapplication.utils.extensions.setImg

class ActorViewHolder(
    private val binding: ViewHolderActorBinding,
    private val actorSelect: (Int, View) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    private var currentActor: Actor? = null

    init {
        binding.root.setOnClickListener {
            val actor = currentActor ?: return@setOnClickListener
            actorSelect(actor.id, binding.root)
        }
    }

    fun bind(actor: Actor) {
        currentActor = actor
        with(binding) {
            root.transitionName = context.getString(R.string.actor_poster_transition_name, actor.id)
            actorImage.setImg(actor.picture)
            actorName.text = actor.name
        }
    }
}