package od.konstantin.myapplication.moviedetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import od.konstantin.myapplication.R
import od.konstantin.myapplication.data.models.Actor
import od.konstantin.myapplication.utils.extensions.context

class ActorsListAdapter : ListAdapter<Actor, ActorsListAdapter.ActorViewHolder>(ActorCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        return ActorViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.view_holder_actor, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        val actor = getItem(position)
        holder.bind(actor)
    }

    class ActorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val actorImage: ImageView = itemView.findViewById(R.id.iv_actor_image)
        private val actorName: TextView = itemView.findViewById(R.id.tv_actor_name)

        fun bind(actor: Actor) {
            Glide.with(context).load(actor.picture).into(actorImage)
            actorName.text = actor.name
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