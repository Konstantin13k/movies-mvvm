package od.konstantin.myapplication.data.mappers.dto

import od.konstantin.myapplication.data.mappers.Mapper
import od.konstantin.myapplication.data.models.Genre
import od.konstantin.myapplication.data.models.MovieDetail
import od.konstantin.myapplication.data.remote.models.GenreDto
import od.konstantin.myapplication.data.remote.models.MovieDetailDto
import od.konstantin.myapplication.utils.BackdropSizes

class MovieDetailDtoMapper(private val genresMapper: Mapper<GenreDto, Genre>) :
    MoviesDtoMapper<MovieDetailDto, MovieDetail>() {

    override fun map(obj: MovieDetailDto): MovieDetail {
        with(obj) {
            return MovieDetail(
                id = id,
                title = title,
                backdropPicture = mapImageUrl(BackdropSizes.W780, backdropPicture),
                genres = genres.map { genresMapper.map(it) },
                ratings = ratings / 2,
                votesCount = votesCount,
                overview = overview ?: "",
                runtime = runtime ?: 0,
                adult = adult,
                actors = emptyList()
            )
        }
    }
}