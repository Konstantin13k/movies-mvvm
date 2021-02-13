package od.konstantin.myapplication.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import od.konstantin.myapplication.data.local.models.FavoriteMovieEmbedded
import od.konstantin.myapplication.data.local.models.FavoriteMovieEntity

@Dao
interface FavoriteMoviesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(movie: FavoriteMovieEntity)

    @Query("SELECT * FROM favorite_movies WHERE favorite_movie_id = :movieId")
    suspend fun selectMovie(movieId: Int): FavoriteMovieEntity?

    @Transaction
    @Query("SELECT * FROM favorite_movies")
    fun observeFavoriteMovies(): Flow<List<FavoriteMovieEmbedded>>

    @Query("SELECT * FROM favorite_movies WHERE favorite_movie_id = :movieId")
    fun observeFavoriteMovieUpdates(movieId: Int): Flow<FavoriteMovieEntity?>

    @Query("SELECT * FROM favorite_movies")
    suspend fun getFavoriteMovies(): List<FavoriteMovieEntity>

    @Query("SELECT * FROM favorite_movies")
    suspend fun getFavoriteMoviesEmbedded(): List<FavoriteMovieEmbedded>

    @Query("DELETE FROM favorite_movies WHERE favorite_movie_id = :movieId")
    suspend fun delete(movieId: Int)

    @Query("DELETE FROM favorite_movies")
    suspend fun deleteAll()
}