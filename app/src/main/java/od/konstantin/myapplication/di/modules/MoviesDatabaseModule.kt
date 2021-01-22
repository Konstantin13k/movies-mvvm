package od.konstantin.myapplication.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import od.konstantin.myapplication.data.local.*

@Module
class MoviesDatabaseModule {

    @Provides
    fun provideMoviesDatabase(context: Context): MoviesDatabase {
        return MoviesDatabase.getInstance(context)
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
}