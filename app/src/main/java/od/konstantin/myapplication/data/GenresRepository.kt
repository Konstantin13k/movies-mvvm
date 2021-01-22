package od.konstantin.myapplication.data

import od.konstantin.myapplication.data.local.GenresDao
import od.konstantin.myapplication.data.local.models.GenreEntity
import od.konstantin.myapplication.data.mappers.dto.GenreDtoMapper
import od.konstantin.myapplication.data.mappers.entity.GenreEntityMapper
import od.konstantin.myapplication.data.models.Genre
import od.konstantin.myapplication.data.remote.GenresApi
import od.konstantin.myapplication.data.remote.models.GenreDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GenresRepository @Inject constructor(
    private val genresDao: GenresDao,
    private val genresApi: GenresApi,
    private val genreDtoMapper: GenreDtoMapper,
    private val genreEntityMapper: GenreEntityMapper,
) {

    suspend fun getGenresByIds(ids: List<Int>, loadIfNotExists: Boolean = true): List<Genre> {
        val genresFromDb = genresDao.getGenres(ids)
        if (genresFromDb.isEmpty() && loadIfNotExists) {
            loadGenres()
            return getGenresByIds(ids, false)
        }
        return genresFromDb.map { genreEntityMapper.map(it) }
    }

    private suspend fun loadGenres() {
        var genresFromDb = loadGenresFromDb()
        if (genresFromDb.isEmpty()) {
            val genresFromNetwork = loadGenresFromNetwork()
            genresFromDb = genresFromNetwork.map { genreDtoMapper.mapToEntity(it) }
            genresDao.insertGenres(genresFromDb)
        }
    }

    private suspend fun loadGenresFromDb(): List<GenreEntity> {
        return genresDao.getGenres()
    }

    private suspend fun loadGenresFromNetwork(): List<GenreDto> {
        return genresApi.getGenres().genres
    }
}