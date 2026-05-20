package com.rozo_garzon.moodflix.domain.repository

import com.rozo_garzon.moodflix.domain.model.Movie
import com.rozo_garzon.moodflix.domain.model.MoodType
import com.rozo_garzon.moodflix.domain.model.Result
import com.rozo_garzon.moodflix.domain.model.TvShow
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMoviesByMood(mood: MoodType, page: Int = 1): Flow<Result<List<Movie>>>
    suspend fun getMovieDetail(movieId: Int): Result<Movie>
    fun searchMovies(query: String, page: Int = 1): Flow<Result<List<Movie>>>
}

interface TvRepository {
    fun getTvByMood(mood: MoodType, page: Int = 1): Flow<Result<List<TvShow>>>
    fun searchTv(query: String, page: Int = 1): Flow<Result<List<TvShow>>>
}
