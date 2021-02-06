package od.konstantin.myapplication.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "actors_details")
data class ActorDetailsEntity(
    @PrimaryKey
    @ColumnInfo(name = "actor_id")
    val actorId: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "known_for_department")
    val knownForDepartment: String,
    @ColumnInfo(name = "birthday")
    val birthday: String,
    @ColumnInfo(name = "place_of_birth")
    val placeOfBirth: String,
    @ColumnInfo(name = "profile_picture")
    val profilePicture: String,
    @ColumnInfo(name = "biography")
    val biography: String,
)