package od.konstantin.myapplication.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "actor_movies",
    foreignKeys = [ForeignKey(
        entity = ActorDetailsEntity::class,
        parentColumns = ["actor_id"],
        childColumns = ["actor_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class ActorMovieEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null,
    @ColumnInfo(name = "movie_id")
    val movieId: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "poster_path")
    val posterPicture: String,
    @ColumnInfo(name = "actor_id", index = true)
    val actorId: Int,
)