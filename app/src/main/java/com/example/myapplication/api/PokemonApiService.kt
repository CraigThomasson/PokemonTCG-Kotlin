package com.example.myapplication.api

import CardResponse
import SetResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiService {
    @GET("cards")
    suspend fun getPokemonCards(
        @Header("Authorization") apiKey: String,
    ): CardResponse

    @GET("sets")
    suspend fun getPokemonSets(
        @Header("Authorization") apiKey: String,
    ): SetResponse

    @GET("cards")
    suspend fun getCardsBySetId(
        @Header("Authorization") apiKey: String,
        @Query("q") query: String
    ): CardResponse
}
