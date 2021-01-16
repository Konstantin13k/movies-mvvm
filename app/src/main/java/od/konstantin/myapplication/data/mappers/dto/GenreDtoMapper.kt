package od.konstantin.myapplication.data.mappers.dto

import od.konstantin.myapplication.data.models.Genre
import od.konstantin.myapplication.data.remote.models.GenreDto

class GenreDtoMapper {

    fun map(genreDto: GenreDto): Genre {
        return Genre(
            genreDto.id,
            genreDto.name,
        )
    }
}