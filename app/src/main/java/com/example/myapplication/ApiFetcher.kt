package com.example.myapplication

import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

fun fetchPokemonData(apiKey: String): String {
    val url = URL("https://api.pokemontcg.io/v2/cards")
    val connection = url.openConnection() as HttpURLConnection
    connection.requestMethod = "GET"
    connection.setRequestProperty("Authorization", "Bearer $apiKey")

    return connection.inputStream.bufferedReader().use { it.readText() }
}

fun searchCardById(jsonResponse: String, cardId: String): String {
    val jsonObject = JSONObject(jsonResponse)
    val cards = jsonObject.getJSONArray("data")

    for (i in 0 until cards.length()) {
        val card = cards.getJSONObject(i)
        if (card.getString("id") == cardId) {
            return card.getString("name")
        }
    }

    return "Card not found"
}