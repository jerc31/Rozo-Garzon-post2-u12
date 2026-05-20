package com.rozo_garzon.moodflix.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbService {
    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("api_key") apiKey: String,
        @Query("with_genres") genres: String,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("vote_count.gte") minVotes: Int = 100,
        @Query("language") language: String = "es-MX",
        @Query("page") page: Int = 1
    ): MovieListResponse

    @GET("discover/tv")
    suspend fun discoverTv(
        @Query("api_key") apiKey: String,
        @Query("with_genres") genres: String,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("vote_count.gte") minVotes: Int = 50,
        @Query("language") language: String = "es-MX",
        @Query("page") page: Int = 1
    ): TvListResponse

    @GET("search/multi")
    suspend fun searchMulti(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("language") language: String = "es-MX",
        @Query("page") page: Int = 1
    ): SearchResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "es-MX"
    ): MovieDetailDto
}

interface OmdbService {
    @GET("/")
    suspend fun getByImdbId(
        @Query("apikey") apiKey: String,
        @Query("i") imdbId: String,
        @Query("plot") plot: String = "full"
    ): OmdbResponse
}
