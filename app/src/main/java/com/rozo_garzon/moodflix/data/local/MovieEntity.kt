package com.rozo_garzon.moodflix.data.local

import androidx.room.*

@Entity(tableName = "mood_cache")
data class MoodCacheEntity(
    @PrimaryKey val moodId: String,
    val moodLabel: String,
    val genreIds: String,
    val lastUpdated: Long,
    val totalResults: Int
)

@Entity(
    tableName = "movies",
    foreignKeys = [ForeignKey(
        entity = MoodCacheEntity::class,
        parentColumns = ["moodId"],
        childColumns = ["moodId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("moodId")]
)
data class MovieEntity(
    @PrimaryKey val id: Int,
    val moodId: String,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val voteAverage: Double,
    val voteCount: Int,
    val releaseDate: String,
    val popularity: Double,
    val cachedAt: Long,
    val mediaType: String = "movie"
)
