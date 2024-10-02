package com.ucb.network

class FilmDataSource(
    val retrofitService: RetrofitBuilder
) {
    suspend fun getInfo(): FilmResponseDto {
        return retrofitService.apiService.getInfo()
    }
}