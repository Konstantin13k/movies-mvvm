package od.konstantin.myapplication.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import od.konstantin.myapplication.data.local.models.*

private const val DATABASE_NAME = "movies.db"

@Database(
    entities = [
        GenreEntity::class,
        MovieDetailsEntity::class,
        FavoriteMovieEntity::class,
        MovieActorEntity::class,
        MovieGenreEntity::class,
    ],
    version = 10,
    exportSchema = false
)
abstract class MoviesDatabase : RoomDatabase() {

    abstract val genresDao: GenresDao

    abstract val movieDetailsDao: MovieDetailsDao

    abstract val movieActorsDao: MovieActorsDao

    abstract val movieGenresDao: MovieGenresDao

    abstract val favoriteMoviesDao: FavoriteMoviesDao

    companion object {

        @Volatile
        private var INSTANCE: MoviesDatabase? = null

        fun getInstance(appContext: Context): MoviesDatabase {
            return INSTANCE ?: Room.databaseBuilder(
                appContext,
                MoviesDatabase::class.java,
                DATABASE_NAME
            ).fallbackToDestructiveMigration()
                .build().also {
                    INSTANCE = it
                }
        }
    }
}