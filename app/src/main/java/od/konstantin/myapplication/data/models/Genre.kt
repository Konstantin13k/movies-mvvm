package od.konstantin.myapplication.data.models

import od.konstantin.myapplication.data.remote.models.JsonGenre

data class Genre(val id: Int, val name: String) {
    constructor(jsonGenre: JsonGenre)
            : this(jsonGenre.id, jsonGenre.name)
}