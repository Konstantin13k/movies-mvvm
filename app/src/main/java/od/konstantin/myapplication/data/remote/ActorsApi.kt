package od.konstantin.myapplication.data.remote

import od.konstantin.myapplication.data.remote.models.ActorDetailsDto
import od.konstantin.myapplication.data.remote.models.ActorMoviesDto
import od.konstantin.myapplication.data.remote.models.CastDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ActorsApi {

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCast(
        @Path("movie_id") movieId: Int,
    ): CastDto?

    @GET("person/{person_id}")
    suspend fun getActorDetails(
        @Path("person_id") actorId: Int,
    ): ActorDetailsDto?

    @GET("discover/movie")
    suspend fun getActorMovies(
        @Query("with_people") actorId: Int,
    ): ActorMoviesDto?
}