package od.konstantin.myapplication.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import od.konstantin.myapplication.data.local.models.MovieDetailsEmbedded
import od.konstantin.myapplication.data.local.models.MovieDetailsEntity

@Dao
interface MovieDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieDetailsEntity: MovieDetailsEntity)

    @Query("SELECT * FROM movie_details WHERE id = :movieId")
    fun getMovieDetails(movieId: Int): LiveData<MovieDetailsEmbedded>

    @Query("DELETE FROM movie_details")
    suspend fun deleteAll()
}