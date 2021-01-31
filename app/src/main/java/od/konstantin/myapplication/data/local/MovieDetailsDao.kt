package od.konstantin.myapplication.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import od.konstantin.myapplication.data.local.models.MovieDetailsEmbedded
import od.konstantin.myapplication.data.local.models.MovieDetailsEntity

@Dao
interface MovieDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieDetailsEntity: MovieDetailsEntity)

    @Transaction
    @Query("SELECT * FROM movie_details WHERE id = :movieId")
    fun observeMovieDetailsUpdates(movieId: Int): Flow<MovieDetailsEmbedded?>

    @Query("DELETE FROM movie_details")
    suspend fun deleteAll()
}