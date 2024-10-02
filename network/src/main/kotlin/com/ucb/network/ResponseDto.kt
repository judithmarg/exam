package com.ucb.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FilmResponseDto(
    @Json(name = "results")
    val results: List<ResponseDto>
)

@JsonClass(generateAdapter = true)
data class ResponseDto(
    @Json(name = "title")
    val title: String,
    @Json(name = "poster_path")
    val posterPath: String,
    @Json(name = "overview")
    val overview: String,
    @Json(name = "release_date")
    val releaseDate: String,
    @Json(name = "vote_average")
    val voteAverage: Double
)

