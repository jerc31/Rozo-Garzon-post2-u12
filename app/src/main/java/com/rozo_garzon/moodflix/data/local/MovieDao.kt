package com.rozo_garzon.moodflix.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("SELECT * FROM movies WHERE moodId = :moodId ORDER BY popularity DESC")
    fun getMoviesByMood(moodId: String): Flow<List<MovieEntity>>

    @Query("DELETE FROM movies WHERE moodId = :moodId")
    suspend fun deleteMoviesByMood(moodId: String)

    @Query("SELECT MAX(cachedAt) FROM movies WHERE moodId = :moodId")
    suspend fun getLastCachedTime(moodId: String): Long?
}

@Dao
interface MoodCacheDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoodCache(cache: MoodCacheEntity)

    @Query("SELECT * FROM mood_cache WHERE moodId = :moodId")
    suspend fun getMoodCache(moodId: String): MoodCacheEntity?
}
