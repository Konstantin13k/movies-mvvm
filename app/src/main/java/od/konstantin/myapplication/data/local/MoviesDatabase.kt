package od.konstantin.myapplication.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import od.konstantin.myapplication.data.local.models.*

@Database(
    entities = [
        GenreEntity::class,
        MovieDetailsEntity::class,
        FavoriteMovieEntity::class,
        MovieActorEntity::class,
        MovieGenreEntity::class,
        ActorDetailsEntity::class,
        ActorMovieEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class MoviesDatabase : RoomDatabase() {

    abstract val genresDao: GenresDao

    abstract val movieDetailsDao: MovieDetailsDao

    abstract val movieActorsDao: MovieActorsDao

    abstract val movieGenresDao: MovieGenresDao

    abstract val favoriteMoviesDao: FavoriteMoviesDao

    abstract val actorDetailsDao: ActorDetailsDao
}