package com.rozo_garzon.moodflix.data.remote

import com.google.gson.annotations.SerializedName

data class MovieListResponse(
    val results: List<MovieDto>,
    val page: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)

data class TvListResponse(
    val results: List<TvShowDto>,
    val page: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)

data class SearchResponse(
    val results: List<SearchResultDto>,
    val page: Int,
    @SerializedName("total_pages") val totalPages: Int
)

data class MovieDto(
    val id: Int,
    val title: String?,
    val overview: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("genre_ids") val genreIds: List<Int>?,
    val popularity: Double
)

data class TvShowDto(
    val id: Int,
    val name: String?,
    val overview: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("first_air_date") val firstAirDate: String?,
    @SerializedName("genre_ids") val genreIds: List<Int>?,
    val popularity: Double
)

data class SearchResultDto(
    val id: Int,
    val title: String?,
    val name: String?,
    val overview: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("first_air_date") val firstAirDate: String?,
    @SerializedName("media_type") val mediaType: String?,
    @SerializedName("genre_ids") val genreIds: List<Int>?,
    val popularity: Double?
)

data class MovieDetailDto(
    val id: Int,
    val title: String,
    val overview: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("release_date") val releaseDate: String?,
    val genres: List<GenreDto>?,
    val popularity: Double,
    val runtime: Int?
)

data class GenreDto(val id: Int, val name: String)

data class OmdbResponse(
    @SerializedName("Title") val title: String?,
    @SerializedName("imdbRating") val imdbRating: String?,
    @SerializedName("Response") val response: String
)
