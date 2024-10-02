package com.ucb.network

class FilmDataSource (
    val retrofitService : RetrofitBuilder
) {
    suspend fun getInfo(themoviedb: String) : ResponseDto {
        return retrofitService.apiService.getInfo(themoviedb)
    }
}