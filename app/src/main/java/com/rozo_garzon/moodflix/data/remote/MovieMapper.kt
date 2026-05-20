package com.rozo_garzon.moodflix.data.remote

import com.rozo_garzon.moodflix.domain.model.Movie
import com.rozo_garzon.moodflix.domain.model.TvShow

private const val IMG_BASE = "https://image.tmdb.org/t/p/w500"
private const val BACKDROP_BASE = "https://image.tmdb.org/t/p/w1280"

private val GENRE_MAP = mapOf(
    28 to "Acción", 12 to "Aventura", 16 to "Animación", 35 to "Comedia",
    80 to "Crimen", 99 to "Documental", 18 to "Drama", 10751 to "Familia",
    14 to "Fantasía", 36 to "Historia", 27 to "Terror", 10402 to "Música",
    9648 to "Misterio", 10749 to "Romance", 878 to "Ciencia Ficción",
    53 to "Suspenso", 10752 to "Guerra", 37 to "Western"
)

object MovieMapper {
    fun fromDto(dto: MovieDto): Movie = Movie(
        id = dto.id,
        title = dto.title ?: "Sin título",
        overview = dto.overview ?: "",
        posterUrl = dto.posterPath?.let { "$IMG_BASE$it" },
        backdropUrl = dto.backdropPath?.let { "$BACKDROP_BASE$it" },
        voteAverage = dto.voteAverage,
        voteCount = dto.voteCount,
        releaseYear = dto.releaseDate?.take(4)?.toIntOrNull() ?: 0,
        genres = dto.genreIds?.mapNotNull { GENRE_MAP[it] } ?: emptyList(),
        popularity = dto.popularity
    )
}

object TvMapper {
    fun fromDto(dto: TvShowDto): TvShow = TvShow(
        id = dto.id,
        name = dto.name ?: "Sin título",
        overview = dto.overview ?: "",
        posterUrl = dto.posterPath?.let { "$IMG_BASE$it" },
        backdropUrl = dto.backdropPath?.let { "$BACKDROP_BASE$it" },
        voteAverage = dto.voteAverage,
        voteCount = dto.voteCount,
        firstAirYear = dto.firstAirDate?.take(4)?.toIntOrNull() ?: 0,
        genres = dto.genreIds?.mapNotNull { GENRE_MAP[it] } ?: emptyList(),
        popularity = dto.popularity
    )
}
