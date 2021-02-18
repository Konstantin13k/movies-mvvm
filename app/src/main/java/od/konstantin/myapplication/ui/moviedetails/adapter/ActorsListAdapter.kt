package od.konstantin.myapplication.ui.moviedetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import od.konstantin.myapplication.R
import od.konstantin.myapplication.data.models.Actor
import od.konstantin.myapplication.databinding.ViewHolderActorBinding
import od.konstantin.myapplication.utils.extensions.context

class ActorsListAdapter(private val actorSelect: (Int) -> Unit) :
    ListAdapter<Actor, ActorViewHolder>(ActorCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        return ActorViewHolder(
            ViewHolderActorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        val actor = getItem(position)
        holder.bind(actor)
        holder.itemView.animation =
            AnimationUtils.loadAnimation(holder.context, R.anim.alpha_recycler_view_animation)

        holder.itemView.setOnClickListener {
            actorSelect(actor.id)
        }
    }
}

class ActorCallback : DiffUtil.ItemCallback<Actor>() {
    override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean {
        return oldItem == newItem
    }
}