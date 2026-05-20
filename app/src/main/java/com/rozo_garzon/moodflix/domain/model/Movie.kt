package com.rozo_garzon.moodflix.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterUrl: String?,
    val backdropUrl: String?,
    val voteAverage: Double,
    val voteCount: Int,
    val releaseYear: Int,
    val genres: List<String>,
    val popularity: Double,
    val mediaType: String = "movie"
)

data class TvShow(
    val id: Int,
    val name: String,
    val overview: String,
    val posterUrl: String?,
    val backdropUrl: String?,
    val voteAverage: Double,
    val voteCount: Int,
    val firstAirYear: Int,
    val genres: List<String>,
    val popularity: Double
)

enum class MoodType(val id: String, val genreIds: List<Int>, val label: String, val color: Long) {
    HAPPY("happy", listOf(35, 10751), "Feliz", 0xFFFFD700),
    SAD("sad", listOf(18, 10749), "Triste", 0xFF4682B4),
    EXCITED("excited", listOf(28, 12), "Emocionado", 0xFFFF4500),
    SCARED("scared", listOf(27, 53), "Asustado", 0xFF4B0082),
    ROMANTIC("romantic", listOf(10749, 18), "Romántico", 0xFFFF69B4),
    BORED("bored", listOf(878, 14), "Aburrido", 0xFF808080),
    NOSTALGIC("nostalgic", listOf(36, 10752), "Nostálgico", 0xFF8B4513),
    CHILL("chill", listOf(99, 10402), "Tranquilo", 0xFF98FB98)
}

sealed class AppException(message: String) : Exception(message) {
    object NoConnection : AppException("Sin conexión a internet")
    object Timeout : AppException("Tiempo de espera agotado")
    data class ServerError(val code: Int) : AppException("Error del servidor: $code")
    object Unauthorized : AppException("API Key inválida")
    object NotFound : AppException("Recurso no encontrado")
    object CacheExpired : AppException("Caché expirado")
    data class Unknown(val errorCause: String) : AppException(errorCause)
}

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: AppException) : Result<Nothing>()
    object Loading : Result<Nothing>()
}
