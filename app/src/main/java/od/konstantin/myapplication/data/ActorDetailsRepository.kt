package od.konstantin.myapplication.data

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext
import od.konstantin.myapplication.data.local.ActorDetailsDao
import od.konstantin.myapplication.data.mappers.dto.ActorDetailsDtoMapper
import od.konstantin.myapplication.data.mappers.dto.ActorMovieDtoMapper
import od.konstantin.myapplication.data.mappers.entity.ActorDetailsEntityMapper
import od.konstantin.myapplication.data.models.ActorDetails
import od.konstantin.myapplication.data.remote.ActorsApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActorDetailsRepository @Inject constructor(
    private val actorsApi: ActorsApi,
    private val actorDetailsDao: ActorDetailsDao,
    private val actorDetailsDtoMapper: ActorDetailsDtoMapper,
    private val actorMovieDtoMapper: ActorMovieDtoMapper,
    private val actorDetailsEntityMapper: ActorDetailsEntityMapper,
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    fun observeActorDetailsUpdates(actorId: Int): Flow<ActorDetails?> {
        return actorDetailsDao.observeActorDetailsUpdates(actorId).mapLatest {
            it?.let { actorDetailsEntityMapper.map(it) }
        }
    }

    suspend fun updateActorData(actorId: Int) = withContext(Dispatchers.IO) {
        try {
            val actorDetailsDto = actorsApi.getActorDetails(actorId)
            val actorMoviesDto = actorsApi.getActorMovies(actorId)?.movies ?: emptyList()

            actorDetailsDto?.let {
                val actorDetailsEntity = actorDetailsDtoMapper.mapToEntity(it)
                val actorMovieEntities = actorMoviesDto.map { actorMovieDto ->
                    actorMovieDtoMapper.mapToEntity(actorDetailsEntity.actorId, actorMovieDto)
                }
                actorDetailsDao.inserActorDetails(actorDetailsEntity)
                actorDetailsDao.insertActorMovies(actorMovieEntities)
            }
        } catch (e: Exception) {
            Log.e("NETWORK", null, e)
            // Todo Handle exceptions
        }
    }
}