package com.example.myapplication.api

import CardResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface PokemonApiService {
    @GET("cards")
    suspend fun getPokemonCards(
        @Header("Authorization") apiKey: String,
    ): CardResponse
}
