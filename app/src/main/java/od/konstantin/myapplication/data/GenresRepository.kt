package od.konstantin.myapplication.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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

    suspend fun getGenresByIds(ids: List<Int>, loadIfNotExists: Boolean = true): List<Genre> =
        withContext(Dispatchers.IO) {
            var genresFromDb = genresDao.getGenres(ids)
            if (genresFromDb.size != ids.size && loadIfNotExists) {
                genresFromDb = updateGenresInDatabase().filter {
                    ids.contains(it.id)
                }
            }
            genresFromDb.map { genreEntityMapper.map(it) }
        }

    private suspend fun updateGenresInDatabase(): List<GenreEntity> {
        val genresFromNetwork = loadGenresFromNetwork()
        val genresFromDb = genresFromNetwork.map { genreDtoMapper.mapToEntity(it) }
        genresDao.insertGenres(genresFromDb)
        return genresFromDb
    }

    private suspend fun loadGenresFromNetwork(): List<GenreDto> {
        return genresApi.getGenres().genres
    }
}