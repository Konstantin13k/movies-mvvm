package od.konstantin.myapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import od.konstantin.myapplication.data.local.models.GenreEntity

@Dao
interface GenresDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genres: List<GenreEntity>)

    @Query("SELECT * FROM genres")
    suspend fun getGenres(): List<GenreEntity>

    @Query("SELECT * FROM genres WHERE id IN (:idList)")
    suspend fun getGenres(idList: List<Int>): List<GenreEntity>

    @Query("DELETE FROM genres")
    suspend fun deleteAll()
}