package com.rozo_garzon.moodflix.data.repository

import com.rozo_garzon.moodflix.BuildConfig
import com.rozo_garzon.moodflix.data.local.MovieDao
import com.rozo_garzon.moodflix.data.local.MoodCacheDao
import com.rozo_garzon.moodflix.data.local.MoodCacheEntity
import com.rozo_garzon.moodflix.data.local.MovieEntity
import com.rozo_garzon.moodflix.data.remote.MovieMapper
import com.rozo_garzon.moodflix.data.remote.TmdbService
import com.rozo_garzon.moodflix.domain.model.*
import com.rozo_garzon.moodflix.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val tmdbService: TmdbService,
    private val movieDao: MovieDao,
    private val moodCacheDao: MoodCacheDao
) : MovieRepository {

    override fun getMoviesByMood(mood: MoodType, page: Int): Flow<Result<List<Movie>>> = flow {
        emit(Result.Loading)
        try {
            val response = tmdbService.discoverMovies(
                apiKey = BuildConfig.TMDB_API_KEY,
                genres = mood.genreIds.joinToString(","),
                page = page
            )
            val movies = response.results.map { MovieMapper.fromDto(it) }
            emit(Result.Success(movies))
            
            // Sync cache
            val entities = response.results.map { dto ->
                MovieEntity(
                    id = dto.id, moodId = mood.id, title = dto.title ?: "",
                    overview = dto.overview ?: "", posterPath = dto.posterPath,
                    voteAverage = dto.voteAverage, voteCount = dto.voteCount,
                    releaseDate = dto.releaseDate ?: "", popularity = dto.popularity,
                    cachedAt = System.currentTimeMillis()
                )
            }
            movieDao.deleteMoviesByMood(mood.id)
            movieDao.insertMovies(entities)
        } catch (e: Exception) {
            val cached = movieDao.getMoviesByMood(mood.id).first()
            if (cached.isNotEmpty()) {
                emit(Result.Success(cached.map { it.toDomain() }))
            } else {
                emit(Result.Error(AppException.Unknown(e.message ?: "")))
            }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getMovieDetail(movieId: Int): Result<Movie> {
        return try {
            val dto = tmdbService.getMovieDetail(movieId, BuildConfig.TMDB_API_KEY)
            Result.Success(MovieMapper.fromDto(
                com.rozo_garzon.moodflix.data.remote.MovieDto(
                    id = dto.id, title = dto.title, overview = dto.overview,
                    posterPath = dto.posterPath, backdropPath = dto.backdropPath,
                    voteAverage = dto.voteAverage, voteCount = dto.voteCount,
                    releaseDate = dto.releaseDate, genreIds = dto.genres?.map { it.id },
                    popularity = dto.popularity
                )
            ))
        } catch (e: Exception) { Result.Error(AppException.Unknown(e.message ?: "")) }
    }

    override fun searchMovies(query: String, page: Int): Flow<Result<List<Movie>>> = flow {
        emit(Result.Loading)
        try {
            val res = tmdbService.searchMulti(BuildConfig.TMDB_API_KEY, query, page = page)
            val movies = res.results.filter { it.mediaType == "movie" }.map { dto ->
                Movie(dto.id, dto.title ?: "Sin título", dto.overview ?: "",
                    dto.posterPath?.let { "https://image.tmdb.org/t/p/w500$it" },
                    dto.backdropPath?.let { "https://image.tmdb.org/t/p/w1280$it" },
                    dto.voteAverage ?: 0.0, 0, dto.releaseDate?.take(4)?.toIntOrNull() ?: 0,
                    emptyList(), dto.popularity ?: 0.0)
            }
            emit(Result.Success(movies))
        } catch (e: Exception) { emit(Result.Error(AppException.Unknown(e.message ?: ""))) }
    }.flowOn(Dispatchers.IO)

    private fun MovieEntity.toDomain() = Movie(
        id, title, overview, posterPath?.let { "https://image.tmdb.org/t/p/w500$it" },
        null, voteAverage, voteCount, releaseDate.take(4).toIntOrNull() ?: 0,
        emptyList(), popularity, mediaType
    )
}
