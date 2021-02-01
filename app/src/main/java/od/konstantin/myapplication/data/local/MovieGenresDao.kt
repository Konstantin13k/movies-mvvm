package od.konstantin.myapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import od.konstantin.myapplication.data.local.models.MovieGenreEntity

@Dao
interface MovieGenresDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genres: List<MovieGenreEntity>)

    @Query("DELETE FROM movie_genres")
    suspend fun deleteAll()
}