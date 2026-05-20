package com.rozo_garzon.moodflix.data.repository

import com.rozo_garzon.moodflix.BuildConfig
import com.rozo_garzon.moodflix.data.remote.TmdbService
import com.rozo_garzon.moodflix.data.remote.TvMapper
import com.rozo_garzon.moodflix.domain.model.*
import com.rozo_garzon.moodflix.domain.repository.TvRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TvRepositoryImpl @Inject constructor(
    private val tmdbService: TmdbService
) : TvRepository {

    override fun getTvByMood(mood: MoodType, page: Int): Flow<Result<List<TvShow>>> = flow {
        emit(Result.Loading)
        try {
            val response = tmdbService.discoverTv(
                apiKey = BuildConfig.TMDB_API_KEY,
                genres = mood.genreIds.joinToString(","),
                page = page
            )
            val tvShows = response.results.map { TvMapper.fromDto(it) }
            emit(Result.Success(tvShows))
        } catch (e: Exception) {
            emit(Result.Error(AppException.Unknown(e.message ?: "Error desconocido")))
        }
    }.flowOn(Dispatchers.IO)

    override fun searchTv(query: String, page: Int): Flow<Result<List<TvShow>>> = flow {
        emit(Result.Loading)
        try {
            val res = tmdbService.searchMulti(BuildConfig.TMDB_API_KEY, query, page = page)
            val tvShows = res.results.filter { it.mediaType == "tv" }.map { dto ->
                TvShow(dto.id, dto.name ?: "Sin título", dto.overview ?: "",
                    dto.posterPath?.let { "https://image.tmdb.org/t/p/w500$it" },
                    dto.backdropPath?.let { "https://image.tmdb.org/t/p/w1280$it" },
                    dto.voteAverage ?: 0.0, 0, dto.firstAirDate?.take(4)?.toIntOrNull() ?: 0,
                    emptyList(), dto.popularity ?: 0.0)
            }
            emit(Result.Success(tvShows))
        } catch (e: Exception) { emit(Result.Error(AppException.Unknown(e.message ?: ""))) }
    }.flowOn(Dispatchers.IO)
}
