package com.rozo_garzon.moodflix.di

import android.content.Context
import androidx.room.Room
import com.rozo_garzon.moodflix.data.local.*
import com.rozo_garzon.moodflix.data.remote.OmdbService
import com.rozo_garzon.moodflix.data.remote.TmdbService
import com.rozo_garzon.moodflix.data.repository.MovieRepositoryImpl
import com.rozo_garzon.moodflix.data.repository.TvRepositoryImpl
import com.rozo_garzon.moodflix.domain.repository.MovieRepository
import com.rozo_garzon.moodflix.domain.repository.TvRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    @Provides @Singleton @Named("tmdb")
    fun provideTmdbRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides @Singleton
    fun provideTmdbService(@Named("tmdb") retrofit: Retrofit): TmdbService =
        retrofit.create(TmdbService::class.java)

    @Provides @Singleton
    fun provideOmdbService(): OmdbService {
        return Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OmdbService::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides @Singleton
    fun provideDatabase(@ApplicationContext ctx: Context): MoodFlixDatabase =
        Room.databaseBuilder(ctx, MoodFlixDatabase::class.java, "moodflix.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides fun provideMovieDao(db: MoodFlixDatabase): MovieDao = db.movieDao()
    @Provides fun provideMoodDao(db: MoodFlixDatabase): MoodCacheDao = db.moodCacheDao()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds @Singleton
    abstract fun bindMovieRepo(impl: MovieRepositoryImpl): MovieRepository

    @Binds @Singleton
    abstract fun bindTvRepo(impl: TvRepositoryImpl): TvRepository
}
