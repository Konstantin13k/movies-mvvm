package od.konstantin.myapplication.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import od.konstantin.myapplication.data.local.*
import javax.inject.Singleton

private const val DATABASE_NAME = "movies.db"

@Module
class MoviesDatabaseModule {

    @Singleton
    @Provides
    fun provideMoviesDatabase(context: Context): MoviesDatabase {
        return Room.databaseBuilder(
            context,
            MoviesDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideGenresDao(database: MoviesDatabase): GenresDao {
        return database.genresDao
    }

    @Provides
    fun provideMovieActorsDao(database: MoviesDatabase): MovieActorsDao {
        return database.movieActorsDao
    }

    @Provides
    fun provideMovieDetailsDao(database: MoviesDatabase): MovieDetailsDao {
        return database.movieDetailsDao
    }

    @Provides
    fun provideMovieGenresDao(database: MoviesDatabase): MovieGenresDao {
        return database.movieGenresDao
    }

    @Provides
    fun provideFavoriteMoviesDao(database: MoviesDatabase): FavoriteMoviesDao {
        return database.favoriteMoviesDao
    }

    @Provides
    fun provideActorDetailsDao(database: MoviesDatabase): ActorDetailsDao {
        return database.actorDetailsDao
    }
}