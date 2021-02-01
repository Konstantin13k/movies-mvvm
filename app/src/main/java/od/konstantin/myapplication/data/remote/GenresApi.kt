package od.konstantin.myapplication.data.remote

import od.konstantin.myapplication.data.remote.models.GenresDto
import retrofit2.http.GET

interface GenresApi {

    @GET("genre/movie/list")
    suspend fun getGenres(): GenresDto
}