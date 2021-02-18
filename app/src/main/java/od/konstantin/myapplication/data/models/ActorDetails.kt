package od.konstantin.myapplication.data.models

import java.util.*

data class ActorDetails(
    val actorId: Int,
    val name: String,
    val knownForDepartment: String,
    val birthday: Date?,
    val placeOfBirth: String,
    val profilePicture: String,
    val biography: String,
    val movies: List<ActorMovie>
)