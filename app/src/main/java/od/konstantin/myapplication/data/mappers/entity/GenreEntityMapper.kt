package od.konstantin.myapplication.data.mappers.entity

import od.konstantin.myapplication.data.local.models.GenreEntity
import od.konstantin.myapplication.data.local.models.MovieGenreEntity
import od.konstantin.myapplication.data.models.Genre
import javax.inject.Inject

class GenreEntityMapper @Inject constructor() {

    fun map(genreEntity: GenreEntity): Genre {
        return Genre(
            genreEntity.id,
            genreEntity.name,
        )
    }

    fun map(movieGenreEntity: MovieGenreEntity): Genre {
        return Genre(
            movieGenreEntity.genreId,
            movieGenreEntity.name,
        )
    }
}