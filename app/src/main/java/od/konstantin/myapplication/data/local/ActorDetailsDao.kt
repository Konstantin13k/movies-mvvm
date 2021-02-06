package od.konstantin.myapplication.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import od.konstantin.myapplication.data.local.models.ActorDetailsEmbedded
import od.konstantin.myapplication.data.local.models.ActorDetailsEntity
import od.konstantin.myapplication.data.local.models.ActorMovieEntity

@Dao
interface ActorDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserActorDetails(details: ActorDetailsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActorMovies(movies: List<ActorMovieEntity>)

    @Transaction
    @Query("SELECT * FROM actors_details WHERE actor_id = :actorId")
    fun observeActorDetailsUpdates(actorId: Int): Flow<ActorDetailsEmbedded?>

    @Query("DELETE FROM actors_details")
    suspend fun deleteAll()
}