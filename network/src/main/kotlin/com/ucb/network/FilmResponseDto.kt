package com.ucb.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class FilmResponseDto (
    @Json(name = "results")
    val results: List<ResponseDto>
)
{}