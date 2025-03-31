package com.example.myapplication.models

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.test.assertEquals
import org.junit.Test

class CardTest {

    @Test
    fun testCardParsing() {
        val json = """
            {
                "id": "xy7-54",
                "name": "Pikachu",
                "imageUrl": "https://images.pokemontcg.io/xy7/54.png",
                "set": {
                    "id": "xy7",
                    "name": "Ancient Origins",
                    "series": "XY",
                    "images": "https://images.pokemontcg.io/xy7/logo.png"
                }
            }
        """
        val card = Json.decodeFromString<Card>(json)

        assertEquals("xy7-54", card.id)
        assertEquals("Pikachu", card.name)
        assertEquals("https://images.pokemontcg.io/xy7/54.png", card.imageUrl)
        assertEquals("xy7", card.set.id)
        assertEquals("Ancient Origins", card.set.name)
        assertEquals("XY", card.set.series)
        assertEquals("https://images.pokemontcg.io/xy7/logo.png", card.set.images)
    }
}