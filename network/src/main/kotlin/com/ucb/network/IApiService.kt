package com.ucb.network

import retrofit2.http.GET

interface IApiService {

    @GET("discover/movie?sort_by=popularity.desc&api_key=fa3e844ce31744388e07fa47c7c5d8c3")
    suspend fun getInfo() : FilmResponseDto


}