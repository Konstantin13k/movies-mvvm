package od.konstantin.myapplication.data.mappers.dto

import od.konstantin.myapplication.data.models.Genre
import od.konstantin.myapplication.data.remote.models.GenreDto

class GenreDtoMapper : MoviesDtoMapper<GenreDto, Genre>() {

    override fun map(obj: GenreDto): Genre {
        return Genre(
            obj.id,
            obj.name,
        )
    }
}