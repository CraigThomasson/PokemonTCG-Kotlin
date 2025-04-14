package com.example.myapplication

import com.example.myapplication.api.PokemonApiService
import com.example.myapplication.api.RetrofitInstance
import com.example.myapplication.models.Card
import kotlinx.coroutines.runBlocking

suspend fun fetchPokemonData(apiKey: String): List<Card> {
    val response = RetrofitInstance.api.getPokemonCards(apiKey)
    return response.data
}

fun searchCardById(
    cards: List<Card>,
    cardId: String,
): String {
    val card = cards.find { it.id == cardId }
    return card?.name ?: "Card not found"
}
