package com.rozo_garzon.moodflix.data.local

import androidx.room.*

@Database(
    entities = [MovieEntity::class, MoodCacheEntity::class],
    version = 3,
    exportSchema = false
)
abstract class MoodFlixDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun moodCacheDao(): MoodCacheDao
}
