package od.konstantin.myapplication.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import od.konstantin.myapplication.data.local.models.FavoriteMovieEmbedded
import od.konstantin.myapplication.data.local.models.FavoriteMovieEntity

@Dao
interface FavoriteMoviesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(movie: FavoriteMovieEntity)

    @Query("SELECT * FROM favorite_movies WHERE favorite_movie_id = :movieId")
    fun selectFavoriteMovie(movieId: Int): LiveData<FavoriteMovieEntity>

    @Query("SELECT * FROM favorite_movies WHERE favorite_movie_id = :movieId")
    suspend fun selectMovie(movieId: Int): FavoriteMovieEntity?

    @Query("DELETE FROM favorite_movies WHERE favorite_movie_id = :movieId")
    suspend fun delete(movieId: Int)

    @Query("DELETE FROM favorite_movies")
    suspend fun deleteAll()
}