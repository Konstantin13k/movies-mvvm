package od.konstantin.myapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import od.konstantin.myapplication.data.local.models.MovieActorEntity

@Dao
interface MovieActorsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActors(actors: List<MovieActorEntity>)

    @Query("DELETE FROM movie_actors")
    suspend fun deleteAll()
}