package od.konstantin.myapplication.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import od.konstantin.myapplication.data.local.GenresDao
import od.konstantin.myapplication.data.local.MoviesDatabase

@Module
class MoviesDatabaseModule {

    @Provides
    fun provideMoviesDatabase(context: Context): MoviesDatabase {
        return MoviesDatabase.getInstance(context)
    }

    @Provides
    fun provideGenresDao(context: Context): GenresDao {
        return provideMoviesDatabase(context).genresDao
    }
}