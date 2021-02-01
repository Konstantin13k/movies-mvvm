package od.konstantin.myapplication.data.remote

import od.konstantin.myapplication.data.remote.models.CastDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ActorsApi {

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCast(
        @Path("movie_id") movieId: Int,
    ): CastDto?
}