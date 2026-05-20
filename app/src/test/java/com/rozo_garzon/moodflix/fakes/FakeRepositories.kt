package com.rozo_garzon.moodflix.fakes

import com.rozo_garzon.moodflix.domain.model.*
import com.rozo_garzon.moodflix.domain.repository.MovieRepository
import com.rozo_garzon.moodflix.domain.repository.TvRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeMovieRepository : MovieRepository {
    var shouldReturnError = false
    var errorException: AppException = AppException.Unknown("Error de prueba")
    
    var moviesList = listOf(
        Movie(1, "Movie 1", "Overview 1", null, null, 8.0, 100, 2024, listOf("Action"), 10.0),
        Movie(2, "Movie 2", "Overview 2", null, null, 7.5, 80, 2023, listOf("Comedy"), 8.0)
    )
    
    var movieDetailResult: Result<Movie> = Result.Success(
        Movie(1, "Movie 1", "Overview 1", null, null, 8.0, 100, 2024, listOf("Action"), 10.0)
    )

    override fun getMoviesByMood(mood: MoodType, page: Int): Flow<Result<List<Movie>>> = flow {
        if (shouldReturnError) {
            emit(Result.Error(errorException))
        } else {
            emit(Result.Success(moviesList))
        }
    }

    override suspend fun getMovieDetail(movieId: Int): Result<Movie> {
        return if (shouldReturnError) {
            Result.Error(errorException)
        } else {
            movieDetailResult
        }
    }

    override fun searchMovies(query: String, page: Int): Flow<Result<List<Movie>>> = flow {
        if (shouldReturnError) {
            emit(Result.Error(errorException))
        } else {
            emit(Result.Success(moviesList))
        }
    }
}

class FakeTvRepository : TvRepository {
    var shouldReturnError = false
    var errorException: AppException = AppException.Unknown("Error de prueba")
    
    var tvShowsList = listOf(
        TvShow(1, "TV Show 1", "Overview 1", null, null, 8.5, 90, 2024, listOf("Drama"), 9.0),
        TvShow(2, "TV Show 2", "Overview 2", null, null, 6.0, 40, 2022, listOf("Sci-Fi"), 5.5)
    )

    override fun getTvByMood(mood: MoodType, page: Int): Flow<Result<List<TvShow>>> = flow {
        if (shouldReturnError) {
            emit(Result.Error(errorException))
        } else {
            emit(Result.Success(tvShowsList))
        }
    }

    override fun searchTv(query: String, page: Int): Flow<Result<List<TvShow>>> = flow {
        if (shouldReturnError) {
            emit(Result.Error(errorException))
        } else {
            emit(Result.Success(tvShowsList))
        }
    }
}
